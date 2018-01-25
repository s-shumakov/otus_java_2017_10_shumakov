package ru.otus.hw11.webserver.base;

import ru.otus.hw11.webserver.base.dataSets.UserDataSet;
import java.util.List;

public interface DBService {

    void save(UserDataSet dataSet);

    UserDataSet read(long id);

    UserDataSet readByName(String name);

    List<UserDataSet> readAll();

    void shutdown();



}
