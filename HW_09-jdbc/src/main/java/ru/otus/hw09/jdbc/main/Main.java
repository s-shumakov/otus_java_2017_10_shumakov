package ru.otus.hw09.jdbc.main;

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

    }
}
