package ru.otus.hw09.jdbc.connection;

import ru.otus.hw09.jdbc.base.DataSet;
import java.sql.SQLException;

public interface DBService extends AutoCloseable {
    void prepareTables() throws SQLException;

    void deleteTables() throws SQLException;

    <T extends DataSet> void addUser(T user) throws SQLException;

    <T extends DataSet> T getUser(long id, Class<T> clazz) throws SQLException;


}
