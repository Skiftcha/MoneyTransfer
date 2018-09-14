package com.revolut.skiftcha.service;

import com.revolut.skiftcha.dao.AccountDAO;
import com.revolut.skiftcha.dao.AccountDAOImpl;
import com.revolut.skiftcha.db.DataSource;
import com.revolut.skiftcha.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public class AccountServiceImpl implements AccountService {
    private static final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Override
    public Collection<Account> listAccounts() {
        try (Connection connection = DataSource.getConnection()) {
            AccountDAO dao = new AccountDAOImpl(connection);
            return dao.listAccounts();
        } catch (SQLException e) {
            LOG.error("Cannot get accounts from database", e);
            throw new InternalServerErrorException(e);
        }
    }

    @Override
    public int getBalance(int id) {
        try (Connection connection = DataSource.getConnection()) {
            AccountDAO dao = new AccountDAOImpl(connection);
            Integer balance = dao.getBalance(id);
            if (balance == null) {
                throw new NotFoundException("Account does not exist");
            } else {
                return balance;
            }
        } catch (SQLException e) {
            LOG.error("Cannot get account from database", e);
            throw new InternalServerErrorException(e);
        }
    }

    @Override
    public void deposit(int id, int amount) {
        try (Connection connection = DataSource.getConnection()) {
            connection.setAutoCommit(false);
            AccountDAO dao = new AccountDAOImpl(connection);
            try {
                Integer balance = dao.getBalance(id);
                if (balance == null) {
                    throw new BadRequestException("Account does not exist");
                }
                dao.setBalance(id, balance + amount);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            LOG.error("Cannot deposit", e);
            throw new InternalServerErrorException(e);
        }
    }

    @Override
    public void withdraw(int id, int amount) {
        try (Connection connection = DataSource.getConnection()) {
            connection.setAutoCommit(false);
            AccountDAO dao = new AccountDAOImpl(connection);
            try {
                Integer balance = dao.getBalance(id);
                if (balance == null) {
                    throw new BadRequestException("Account does not exist");
                }
                if (balance < amount) {
                    throw new BadRequestException("Not enough money for withdrawal");
                }
                dao.setBalance(id, balance - amount);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            LOG.error("Cannot withdraw", e);
            throw new InternalServerErrorException(e);
        }
    }

    @Override
    public void transfer(int from, int to, int amount) {
        try (Connection connection = DataSource.getConnection()) {
            connection.setAutoCommit(false);
            AccountDAO dao = new AccountDAOImpl(connection);
            try {
                Integer fromBalance;
                Integer toBalance;
                // prevent deadlock
                if (from < to) {
                    fromBalance = dao.getBalance(from);
                    toBalance = dao.getBalance(to);
                } else {
                    toBalance = dao.getBalance(to);
                    fromBalance = dao.getBalance(from);
                }
                if (fromBalance == null) {
                    throw new BadRequestException("Source account does not exist");
                }
                if (toBalance == null) {
                    throw new BadRequestException("Destination account does not exist");
                }
                if (fromBalance < amount) {
                    throw new BadRequestException("Not enough money for transfer");
                }
                dao.setBalance(from, fromBalance - amount);
                dao.setBalance(to, toBalance + amount);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            LOG.error("Cannot transfer", e);
            throw new InternalServerErrorException(e);
        }
    }
}
