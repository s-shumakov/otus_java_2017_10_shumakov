package ru.otus.hw09.jdbc.main;

import java.util.List;

import ru.otus.hw09.jdbc.base.DBService;
import ru.otus.hw09.jdbc.base.dataSets.UserDataSet;
import ru.otus.hw09.jdbc.dbService.DBServiceImpl;

public class Main {
    public static void main(String[] args) throws Exception {
        DBService dbService = new DBServiceImpl();
        dbService.save(new UserDataSet(0, "bill", 20, "123456"));
        dbService.save(new UserDataSet(0, "bob", 55,"78901"));

        System.out.println("User by id: " + dbService.read(1));
        System.out.println("User by name: " + dbService.readByName("bob"));

        List<UserDataSet> users = dbService.readAll();
        System.out.println("All users: " + users.toString());

        dbService.shutdown();
    }
}
