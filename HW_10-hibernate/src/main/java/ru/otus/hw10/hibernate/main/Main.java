package ru.otus.hw10.hibernate.main;

import ru.otus.hw10.hibernate.base.DBService;
import ru.otus.hw10.hibernate.base.dataSets.*;
import ru.otus.hw10.hibernate.dbService.DBServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        DBService dbService = new DBServiceImpl();

        Set<PhoneDataSet> phones1 = new HashSet<>();
        Set<PhoneDataSet> phones2 = new HashSet<>();
        Set<PhoneDataSet> phones3 = new HashSet<>();
        phones1.add(new PhoneDataSet("123-456-7890"));
        phones1.add(new PhoneDataSet("321-654-0987"));
        phones2.add(new PhoneDataSet("345-678-1234"));
        phones3.add(new PhoneDataSet("234-345-5678"));
        phones3.add(new PhoneDataSet("321-654-7654"));
        phones3.add(new PhoneDataSet("321-654-5432"));

        dbService.save(new UserDataSet("billy", phones1, new AddressDataSet("1st street")));
        dbService.save(new UserDataSet("willy", phones2, new AddressDataSet("2st street")));
        dbService.save(new UserDataSet("dilly", phones3, new AddressDataSet("3st street")));

        UserDataSet dataSet = dbService.read(1);
        System.out.println(dataSet);

        dataSet = dbService.readByName("billy");
        System.out.println(dataSet);

        List<UserDataSet> dataSets = dbService.readAll();
        for (UserDataSet userDataSet : dataSets) {
            System.out.println(userDataSet);
        }

        dbService.shutdown();
    }
}
