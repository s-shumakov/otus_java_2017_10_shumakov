package ru.otus.hw09.jdbc.orm;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

public class Executor {
    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    Connection getConnection() {
        return connection;
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
            return handler.handle(result);
        }
    }

    <T extends DataSet> void save(T user) {
        String query = "insert into user ";
        Map map = new HashMap<String, String>();
        try {
            Class clazz = user.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                map.put(field.getName(), "\"" + ReflectionHelper.getFieldValue(user, field.getName()).toString() + "\"");
            }
            List<String> fieldNames = new ArrayList<>();
            List<String> fieldValues = new ArrayList<>();
            map.forEach((k, v) -> {
                fieldNames.add(k.toString());
                fieldValues.add("\"" + v.toString() + "\"");
            });

//            query += "(" + String.join(", ", fieldNames) + ") values (" + String.join(", ", fieldValues) + ")";
            query += "(" + String.join(", ", map.keySet()) + ") values (" + String.join(", ", map.values()) + ")";
            System.out.println("Query: " + query);

            Statement stmt = getConnection().createStatement();
            stmt.execute(query);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    <T extends DataSet> T load(long id, Class<T> clazz) {
        return null;
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
