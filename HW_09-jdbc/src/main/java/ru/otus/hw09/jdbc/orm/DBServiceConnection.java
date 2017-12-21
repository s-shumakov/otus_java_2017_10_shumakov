package ru.otus.hw09.jdbc.orm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBServiceConnection implements DBService {
    private static final String CREATE_TABLE_USER = "create table if not exists user " +
            "(id bigint(20) not null auto_increment, " +
            "name varchar(256), " +
            "age int(3),  " +
            "primary key (id))";
    private static final String INSERT_INTO_USER = "insert into user (name, age) values(?, ?)";
    private static final String DELETE_USER = "drop table user";
    private static final String SELECT_USER = "select name from user where id=%s";
    private static final String SELECT_ALL_USERS = "select * from user";

    private final Connection connection;

    public DBServiceConnection() {
        connection = ConnectionHelper.getConnection();
    }

    public String getMetaData() {
        try {
            return "Connected to: " + getConnection().getMetaData().getURL() + "\n" +
                    "DB name: " + getConnection().getMetaData().getDatabaseProductName() + "\n" +
                    "DB version: " + getConnection().getMetaData().getDatabaseProductVersion() + "\n" +
                    "Driver: " + getConnection().getMetaData().getDriverName();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public <T extends DataSet> void saveUser(T user) {
        Executor exec = new Executor(getConnection());
        exec.save(user);
    }

    public void prepareTables() throws SQLException {
        Executor exec = new Executor(getConnection());
        exec.execUpdate(CREATE_TABLE_USER, statement -> {
            statement.execute();
        });
        System.out.println("Table created");
    }

    public void deleteTables() throws SQLException {
        Executor exec = new Executor(getConnection());
        exec.execUpdate(DELETE_USER, statement -> {
            statement.execute();
        });
        System.out.println("Table dropped");
    }

    public void addUsersByName(String... names) throws SQLException {
        Executor exec = new Executor(getConnection());
        exec.execUpdate(INSERT_INTO_USER, statement -> {
            for (String name : names) {
                statement.setString(1, name);
                statement.setString(2, null);
                statement.execute();
            }
        });
        System.out.println("Users by name added");
    }

    public void addUsers(UsersDataSet... useres) throws SQLException {
        Executor exec = new Executor(getConnection());
        exec.execUpdate(INSERT_INTO_USER, statement -> {
            for (UsersDataSet user : useres) {
                statement.setString(1, user.getName());
                statement.setLong(2, user.getAge());
                statement.execute();
            }
        });
    }

    public String getUserName(int id) throws SQLException {
        Executor executor = new Executor(getConnection());

        return executor.execQuery(String.format(SELECT_USER, id), result -> {
            result.next();
            return result.getString("name");
        });
    }

    public List<String> getAllNames() throws SQLException {
        Executor executor = new Executor(getConnection());

        return executor.execQuery("select name from user", result -> {
            List<String> names = new ArrayList<>();

            while (!result.isLast()) {
                result.next();
                names.add(result.getString("name"));
            }
            return names;
        });
    }

    public List<UsersDataSet> getAllUsers() throws SQLException {
        Executor executor = new Executor(getConnection());

        return executor.execQuery(SELECT_ALL_USERS, result -> {
            List<UsersDataSet> users = new ArrayList<>();

            while (!result.isLast()) {
                result.next();
                users.add(new UsersDataSet(result.getLong("id"),
                        result.getString("name"),
                        result.getInt("age")));
            }
            return users;
        });
    }

    public void close() throws Exception {
        connection.close();
        System.out.println("Connection closed. Bye!");
    }

    protected Connection getConnection() {
        return connection;
    }


}
