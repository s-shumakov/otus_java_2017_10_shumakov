package ru.otus.hw11.cache.main;

import ru.otus.hw11.cache.base.DBService;
import ru.otus.hw11.cache.base.dataSets.*;
import ru.otus.hw11.cache.dbService.DBServiceImpl;

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

        dbService.save(new UserDataSet("user1", phones1, new AddressDataSet("1st street")));
        dbService.save(new UserDataSet("user2", phones2, new AddressDataSet("2st street")));
        dbService.save(new UserDataSet("user3", phones3, new AddressDataSet("3st street")));
        dbService.save(new UserDataSet("user4", new PhoneDataSet("2345678"), new AddressDataSet("4st street")));
        dbService.save(new UserDataSet("user5", new PhoneDataSet("7654356"), new AddressDataSet("5st street")));
        dbService.save(new UserDataSet("user6", new PhoneDataSet("5245753"), new AddressDataSet("6st street")));
        dbService.save(new UserDataSet("user7", new PhoneDataSet("8467467"), new AddressDataSet("7st street")));
        dbService.save(new UserDataSet("user8", new PhoneDataSet("2378732"), new AddressDataSet("8st street")));
        dbService.save(new UserDataSet("user9", new PhoneDataSet("6546657"), new AddressDataSet("9st street")));
        dbService.save(new UserDataSet("user10", new PhoneDataSet("6786733"), new AddressDataSet("10st street")));

        UserDataSet dataSet = dbService.read(1);
        System.out.println(dataSet);
        dataSet = dbService.read(5);
        System.out.println(dataSet);
        dataSet = dbService.read(7);
        dataSet = dbService.read(7);
        System.out.println(dataSet);

        dataSet = dbService.readByName("user1");
        System.out.println(dataSet);
        dataSet = dbService.readByName("user5");
        System.out.println(dataSet);
        dataSet = dbService.readByName("user6");
        dataSet = dbService.readByName("user6");
        System.out.println(dataSet);

        List<UserDataSet> dataSets = dbService.readAll();
        for (UserDataSet userDataSet : dataSets) {
            System.out.println(userDataSet);
        }

        dbService.shutdown();
    }
}
