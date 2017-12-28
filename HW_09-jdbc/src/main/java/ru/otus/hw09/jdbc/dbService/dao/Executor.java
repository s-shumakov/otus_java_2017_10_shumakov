package ru.otus.hw09.jdbc.dbService.dao;

import ru.otus.hw09.jdbc.base.dataSets.DataSet;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

public class Executor {
    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public <T extends DataSet> void save(T user, String table) throws SQLException {
        Map map = new HashMap<String, String>();
        Class clazz = user.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            map.put(field.getName(), "\"" + ReflectionHelper.getFieldValue(user, field.getName()).toString() + "\"");
        }
        String query = buildInsertQuery(table, map);
        Statement stmt = getConnection().createStatement();
        stmt.execute(query);
        stmt.close();
    }

    public <T extends DataSet> T load(String query, Class<T> clazz) throws SQLException {
        Statement stmt = getConnection().createStatement();
        stmt.execute(query);
        ResultSet result = stmt.getResultSet();
        T user = createUserDataSet(clazz, result).get(0);
        result.close();
        stmt.close();
        return user;
    }

    public <T extends DataSet> List<T> loadAll(String query, Class<T> clazz) throws SQLException {
        Statement stmt = getConnection().createStatement();
        stmt.execute(query);
        ResultSet result = stmt.getResultSet();
        List<T> users = createUserDataSet(clazz, result);
        result.close();
        stmt.close();
        return users;
    }

    private <T> List<T> createUserDataSet(Class<T> clazz, ResultSet result) throws SQLException {
        ResultSetMetaData rsmd = result.getMetaData();
        int columnCount = rsmd.getColumnCount();
        List<String> columns = new ArrayList<>();

        for (int i = 1; i <= columnCount; i++) {
            columns.add(rsmd.getColumnName(i));
        }

        List<T> users = new ArrayList<>();
        while (!result.isLast()) {
            result.next();
            T object = ReflectionHelper.instantiate(clazz);
            for (String col : columns) {
                ReflectionHelper.setFieldValue(object, col, result.getObject(col));
            }
            users.add(object);
        }
        return users;
    }

    private String buildInsertQuery(String table, Map map) {
        List<String> fieldNames = new ArrayList<>();
        List<String> fieldValues = new ArrayList<>();
        map.forEach((k, v) -> {
            fieldNames.add(k.toString());
            fieldValues.add("\"" + v.toString() + "\"");
        });
        String query = "insert into " + table +
                " (" + String.join(", ", map.keySet()) + ")" +
                " values (" + String.join(", ", map.values()) + ")";
        System.out.println("Query: " + query);
        return query;
    }


    public void execUpdate(String update, ExecuteHandler prepare) {
        try {
            PreparedStatement stmt = getConnection().prepareStatement(update);
            prepare.accept(stmt);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <T> T execQuery(String query, ResultHandler<T> handler) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
            ResultSet result = stmt.getResultSet();
            stmt.close();
            return handler.handle(result);
        }
    }

    Connection getConnection() {
        return connection;
    }

    @FunctionalInterface
    public interface ResultHandler<T> {
        T handle(ResultSet resultSet) throws SQLException;
    }

    @FunctionalInterface
    public interface ExecuteHandler {
        void accept(PreparedStatement statement) throws SQLException;
    }
}
