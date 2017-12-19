package ru.otus.hw09.jdbc.connection;

import ru.otus.hw09.jdbc.base.DataSet;
import ru.otus.hw09.jdbc.base.UsersDataSet;
import ru.otus.hw09.jdbc.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

public class DBServiceConnection implements DBService {
    private static final String CREATE_TABLE_USER = "mysql> create table if not exists user " +
            "(id bigint(20) not null auto_increment, " +
            "name varchar(256), " +
            "age int(3),  " +
            "primary key (id))";
    private static final String INSERT_USER = "insert into user (name) values ('%s')";
    private static final String DELETE_USER = "drop table user";
    private static final String SELECT_USER = "select name from user where id=%s";

    private final Connection connection;

    public DBServiceConnection() {
        connection = ConnectionHelper.getConnection();
    }

    @Override
    public void prepareTables() throws SQLException {
        Executor exec = new Executor(getConnection());
        exec.execUpdate(CREATE_TABLE_USER);
        System.out.println("Table created");
    }

    @Override
    public void deleteTables() throws SQLException {
        Executor exec = new Executor(getConnection());
        exec.execUpdate(DELETE_USER);
        System.out.println("Table dropped");
    }

    public String getUserName(int id) throws SQLException {
        Executor exec = new Executor(getConnection());

        return exec.execQuery(String.format(SELECT_USER, id), result -> {
            result.next();
            return result.getString("name");
        });
    }

    @Override
    public <T extends DataSet> void addUser(T user) throws SQLException {
        Executor exec = new Executor(getConnection());
        exec.save(new UsersDataSet(1, "", 1));
    }

    @Override
    public <T extends DataSet> T getUser(long id, Class<T> clazz) throws SQLException {
        return null;
    }

    @Override
    public void close() throws Exception {
        connection.close();
        System.out.println("Connection closed. Bye!");
    }

    protected Connection getConnection() {
        return connection;
    }


}
