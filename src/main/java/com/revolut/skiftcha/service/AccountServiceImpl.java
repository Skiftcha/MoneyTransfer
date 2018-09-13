package com.revolut.skiftcha.service;

import com.revolut.skiftcha.dao.AccountDAO;
import com.revolut.skiftcha.dao.AccountDAOImpl;
import com.revolut.skiftcha.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import java.util.List;

public class AccountServiceImpl implements AccountService {
    private static final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Override
    public List<Account> listAccounts() {
        try (AccountDAO dao = new AccountDAOImpl()) {
            return dao.listAccounts();
        } catch (Exception e) {
            LOG.error("Cannot get accounts from database", e);
            throw new InternalServerErrorException(e);
        }
    }

    @Override
    public int getBalance(int id) {
        try (AccountDAO dao = new AccountDAOImpl()) {
            Integer balance = dao.getBalance(id);
            if (balance == null) {
                throw new NotFoundException("Account not found");
            } else {
                return balance;
            }
        } catch (Exception e) {
            LOG.error("Cannot get accounts from database", e);
            throw new InternalServerErrorException(e);
        }
    }

    @Override
    public void deposit(int id, int amount) {
        try (AccountDAO dao = new AccountDAOImpl()) {
            int balance = dao.getBalance(id);
            dao.setBalance(id, balance + amount);
            dao.commit();
        } catch (Exception e) {
            LOG.error("Cannot get accounts from database", e);
            throw new InternalServerErrorException(e);
        }
    }

    @Override
    public void withdraw(int id, int amount) {
        try (AccountDAO dao = new AccountDAOImpl()) {
            int balance = dao.getBalance(id);
            if (balance < amount) {
                throw new BadRequestException("Not enough money for withdrawal");
            }
            dao.setBalance(id, balance + amount);
            dao.commit();
        } catch (Exception e) {
            LOG.error("Cannot get accounts from database", e);
            throw new InternalServerErrorException(e);
        }
    }

    @Override
    public void transfer(int from, int to, int amount) {
        try (AccountDAO dao = new AccountDAOImpl()) {
            int fromBalance;
            int toBalance;
            // prevent deadlock
            if (from < to) {
                fromBalance = dao.getBalance(from);
                toBalance = dao.getBalance(to);
            } else {
                toBalance = dao.getBalance(to);
                fromBalance = dao.getBalance(from);
            }
            if (fromBalance < amount) {
                throw new BadRequestException("Not enough money for transfer");
            }
            dao.setBalance(from, fromBalance - amount);
            dao.setBalance(to, toBalance + amount);
            dao.commit();
        } catch (Exception e) {
            LOG.error("Cannot get accounts from database", e);
            throw new InternalServerErrorException(e);
        }
    }
}
