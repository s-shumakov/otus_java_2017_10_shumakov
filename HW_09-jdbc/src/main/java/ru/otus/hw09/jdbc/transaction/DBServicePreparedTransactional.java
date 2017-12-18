package ru.otus.hw09.jdbc.transaction;

import ru.otus.hw09.jdbc.executor.PreparedExecutor;
import ru.otus.hw09.jdbc.prepared.DBServicePrepared;

import java.sql.SQLException;

public class DBServicePreparedTransactional extends DBServicePrepared {
    private static final String INSERT_INTO_USER = "insert into user (name) values(?)";

    @Override
    public void addUsers(String... names) throws SQLException {
        try {
            PreparedExecutor exec = new PreparedExecutor(getConnection());
            getConnection().setAutoCommit(false);
            exec.execUpdate(INSERT_INTO_USER, statement -> {
                for (String name : names) {
                    statement.setString(1, name);
                    statement.execute();
                }
            });
            getConnection().commit();
        } catch (SQLException e){
            getConnection().rollback();
        } finally {
            getConnection().setAutoCommit(true);
        }
    }
}
