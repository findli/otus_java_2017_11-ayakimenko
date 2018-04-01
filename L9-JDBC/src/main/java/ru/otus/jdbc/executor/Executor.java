package ru.otus.jdbc.executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Wrapper for executing queries
 *
 * Created by abyakimenko on 30.03.2018.
 */
public class Executor {

    private final Connection connection;


    public Executor(Connection connection) {
        this.connection = connection;
    }

    public int executeUpdate(String sql) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }

    public <T> T executeQuery(String sql, TResultHandler<T> handler) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            return handler.handle(stmt.executeQuery(sql));
        }
    }
}
