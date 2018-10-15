package com.revolut.skiftcha.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private static final String JDBC_URL = "jdbc:h2:mem:money;DB_CLOSE_DELAY=-1;MULTI_THREADED=TRUE;" +
            "INIT=runscript from 'classpath:/db.sql'";

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl(JDBC_URL);
        ds = new HikariDataSource(config);
    }

    private DataSource() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
