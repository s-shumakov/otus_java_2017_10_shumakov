package ru.otus.hw09.jdbc.dbService;

import ru.otus.hw09.jdbc.base.DBService;
import ru.otus.hw09.jdbc.base.dataSets.UserDataSet;
import ru.otus.hw09.jdbc.dbService.dao.UserDataSetDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBServiceImpl implements DBService {
    private final Connection connection;

    public DBServiceImpl() {
        connection = ConnectionHelper.getConnection();
        prepareTables();
    }

    public void prepareTables() {
        UserDataSetDAO dao = new UserDataSetDAO(connection);
        dao.createTable();
    }

    public void save(UserDataSet dataSet) {
        UserDataSetDAO dao = new UserDataSetDAO(connection);
        try {
            dao.save(dataSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserDataSet read(long id) {
        UserDataSetDAO dao = new UserDataSetDAO(connection);
        try {
            return dao.read(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserDataSet readByName(String name) {
        UserDataSetDAO dao = new UserDataSetDAO(connection);
        try {
            return dao.readByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<UserDataSet> readAll() {
        UserDataSetDAO dao = new UserDataSetDAO(connection);
        try {
            return dao.readAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }

    public void shutdown() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connection closed. Bye!");
    }

}
