package ru.otus.hw09.jdbc.executor;

import ru.otus.hw09.jdbc.base.DataSet;

import java.sql.*;

public class Executor {
    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public <T extends DataSet> void save(T user) throws SQLException {
//        try {
//            PreparedStatement stmt = getConnection().prepareStatement(update);
//            prepare.accept(stmt);
//            stmt.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public <T> T load(String query, TResultHandler<T> handler) throws SQLException {
        try(Statement stmt = connection.createStatement()) {
            stmt.execute(query);
            ResultSet result = stmt.getResultSet();
            return handler.handle(result);
        }
    }

    Connection getConnection() {
        return connection;
    }

    public int execUpdate(String update) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(update);
            return stmt.getUpdateCount();
        }
    }

    public <T> T execQuery(String query, TResultHandler<T> handler) throws SQLException {
        try(Statement stmt = connection.createStatement()) {
            stmt.execute(query);
            ResultSet result = stmt.getResultSet();
            return handler.handle(result);
        }
    }

    @FunctionalInterface
    public interface TResultHandler<T> {
        T handle(ResultSet resultSet) throws SQLException;
    }

    @FunctionalInterface
    public interface ResultHandler {
        void handle(ResultSet result) throws SQLException;
    }
}
