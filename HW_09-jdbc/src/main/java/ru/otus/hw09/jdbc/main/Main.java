package ru.otus.hw09.jdbc.main;

import java.util.List;

import ru.otus.hw09.jdbc.orm.*;

public class Main {
    public static void main(String[] args) throws Exception {
        try (DBService dbService = new DBServiceConnection()) {
            System.out.println(dbService.getMetaData());
            dbService.prepareTables();
            dbService.addUsersByName("tully", "sully");
            dbService.addUsers(
                    new UsersDataSet(0, "bill", 20),
                    new UsersDataSet(0, "bob", 30)
            );
            dbService.saveUser(new UsersDataSet(0, "john", 33));
            System.out.println("UserName with id = 1: " + dbService.getUserName(1));
            List<String> names = dbService.getAllNames();
            System.out.println("All names: " + names.toString());
            List<UsersDataSet> users = dbService.getAllUsers();
            System.out.println("All users: " + users.toString());
//            dbService.deleteTables();
        }
    }
}
