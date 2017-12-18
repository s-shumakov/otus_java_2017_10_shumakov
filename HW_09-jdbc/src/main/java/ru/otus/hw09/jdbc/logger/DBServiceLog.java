package ru.otus.hw09.jdbc.logger;

import ru.otus.hw09.jdbc.connection.DBServiceUpdate;
import ru.otus.hw09.jdbc.executor.LogExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBServiceLog extends DBServiceUpdate {
    private static final String SELECT_USER = "select name from user where id=%s";

    @Override
    public String getUserName(int id) throws SQLException {
        LogExecutor exec = new LogExecutor(getConnection());
        //with inner class
        exec.execQuery(String.format(SELECT_USER, id), new ResultHandlerGetName());

        //with anonymous class
        exec.execQuery(String.format(SELECT_USER, id),
                new ResultHandler() {
                    @Override
                    public void handle(ResultSet result) throws SQLException {
                        result.next();
                        System.out.println("Read user: " + result.getString("name"));
                    }
                }
        );

        //with lambda
        exec.execQuery(String.format(SELECT_USER, id),
                result -> {
                    result.next();
                    System.out.println("Read user: " + result.getString("name"));
                });

        return null;
    }

    private static class ResultHandlerGetName implements ResultHandler {
        public void handle(ResultSet result) throws SQLException {
            result.next();
            System.out.println("Read user: " + result.getString("name"));
        }
    }
}
