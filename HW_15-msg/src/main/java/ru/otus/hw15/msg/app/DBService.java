package ru.otus.hw15.msg.app;

import ru.otus.hw15.msg.cache.CacheEngine;
import ru.otus.hw15.msg.dataSets.UserDataSet;
import ru.otus.hw15.msg.messageSystem.Addressee;
import ru.otus.hw15.msg.messageSystem.MessageSystem;

import java.util.List;

public interface DBService extends Addressee {
    void init();

    void save(UserDataSet dataSet);

    UserDataSet read(long id);

    UserDataSet readByName(String name);

    List<UserDataSet> readAll();

    void shutdown();

    MessageSystem getMS();

    CacheEngine getCache();
}
