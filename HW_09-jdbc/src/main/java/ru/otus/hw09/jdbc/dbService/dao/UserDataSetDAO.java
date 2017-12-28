package ru.otus.hw09.jdbc.dbService.dao;

import ru.otus.hw09.jdbc.base.dataSets.UserDataSet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserDataSetDAO {
    private final Connection connection;
    private Executor executor;
    public final static String TABLE_NAME = "user";
    private static final String CREATE_TABLE = "create table if not exists " + TABLE_NAME +
            " (id bigint(20) not null auto_increment," +
            " name varchar(256)," +
            " age int(3), " +
            " phone varchar(50), " +
            " primary key (id))";
    private static final String DELETE_TABLE = "drop table " + TABLE_NAME;
    private static final String SELECT_BY_NAME = "select * from " + TABLE_NAME + " where name='%s'";
    private static final String SELECT_BY_ID = "select * from " + TABLE_NAME + " where id=%s";
    private static final String SELECT_ALL = "select * from " + TABLE_NAME;

    public UserDataSetDAO(Connection connection) {
        this.connection = connection;
        this.executor = new Executor(connection);
    }

    public void save(UserDataSet dataSet) throws SQLException {
        executor.save(dataSet, TABLE_NAME);
    }

    public UserDataSet read(long id) throws SQLException {
        return executor.load(String.format(SELECT_BY_ID, id), UserDataSet.class);
    }

    public UserDataSet readByName(String name) throws SQLException {
        return executor.load(String.format(SELECT_BY_NAME, name), UserDataSet.class);
    }

    public List<UserDataSet> readAll() throws SQLException {
        return executor.loadAll(String.format(SELECT_ALL), UserDataSet.class);
    }

    public void createTable() {
        executor.execUpdate(CREATE_TABLE, statement -> {
            statement.execute();
        });
        System.out.println(String.format("Table %s created", TABLE_NAME));
    }

    public void dropTable() {
        executor.execUpdate(DELETE_TABLE, statement -> {
            statement.execute();
        });
        System.out.println(String.format("Table %s dropped", TABLE_NAME));
    }

}
