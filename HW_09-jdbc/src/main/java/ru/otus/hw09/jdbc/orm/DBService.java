package ru.otus.hw09.jdbc.orm;

import java.sql.SQLException;
import java.util.List;

public interface DBService extends AutoCloseable {

//    <T extends DataSet> void addUser(T user) throws SQLException;

//    <T extends DataSet> T getUser(long id, Class<T> clazz) throws SQLException;

    String getMetaData();

    void prepareTables() throws SQLException;

    void addUsersByName(String... names) throws SQLException;

    void addUsers(UsersDataSet... useres) throws SQLException;

    String getUserName(int id) throws SQLException;

    List<String> getAllNames() throws SQLException;

    List<UsersDataSet> getAllUsers() throws SQLException;

    void deleteTables() throws SQLException;

    <T extends DataSet> void saveUser(T user);
}
