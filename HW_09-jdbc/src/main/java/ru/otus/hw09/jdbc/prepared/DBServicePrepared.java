package ru.otus.hw09.jdbc.prepared;

import ru.otus.hw09.jdbc.executor.PreparedExecutor;
import ru.otus.hw09.jdbc.executor.TExecutor;
import ru.otus.hw09.jdbc.simple.DBServiceSimple;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBServicePrepared extends DBServiceSimple {

    private static final String INSERT_INTO_USER = "insert into user (name) values(?)";

    @Override
    public void addUsers(String... names) throws SQLException {
        PreparedExecutor exec = new PreparedExecutor(getConnection());
        exec.execUpdate(INSERT_INTO_USER, statement -> {
            for (String name : names) {
                statement.setString(1, name);
                statement.execute();
            }
        });
    }

    @Override
    public List<String> getAllNames() throws SQLException {
        TExecutor executor = new TExecutor(getConnection());

        return executor.execQuery("select name from user", result -> {
            List<String> names = new ArrayList<>();

            while (!result.isLast()) {
                result.next();
                names.add(result.getString("name"));
            }
            return names;
        });
    }
}
