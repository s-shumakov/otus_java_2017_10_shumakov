package ru.otus.hw09.jdbc.main;

import ru.otus.hw09.jdbc.base.DBService;
import ru.otus.hw09.jdbc.base.UsersDataSet;
import ru.otus.hw09.jdbc.connection.DBServiceConnection;
import ru.otus.hw09.jdbc.connection.DBServiceUpdate;
import ru.otus.hw09.jdbc.logger.DBServiceLog;
import ru.otus.hw09.jdbc.prepared.DBServicePrepared;
import ru.otus.hw09.jdbc.simple.DBServiceSimple;
import ru.otus.hw09.jdbc.transaction.DBServicePreparedTransactional;

import java.util.List;

/**
 * mysql> CREATE USER 'test'@'localhost' IDENTIFIED BY 'test';
 * mysql> GRANT ALL PRIVILEGES ON db_example. * TO 'test'@'localhost';
 * mysql> select user, host from mysql.user;
 * mysql> create database db_example;
 * mysql> SET GLOBAL time_zone = '+3:00';
 */

public class Main {
    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {
//        try(DBService dbService = new DBServiceConnection()) {
        try (DBService dbService = new DBServiceUpdate()) {
//        try (DBService dbService = new DBServiceLog()) {
//        try (DBService dbService = new DBServiceSimple()) {
//        try (DBService dbService = new DBServicePrepared()) {
//        try (DBService dbService = new DBServicePreparedTransactional()) {
            System.out.println(dbService.getMetaData());
            dbService.prepareTables();
            dbService.addUsers("tully", "sully");
            System.out.println("UserName with id = 1: " + dbService.getUserName(1));
            List<String> names = dbService.getAllNames();
            System.out.println("All names: " + names.toString());
            List<UsersDataSet> users = dbService.getAllUsers();
            System.out.println("All users: " + users.toString());
            dbService.deleteTables();
        }
    }
}
