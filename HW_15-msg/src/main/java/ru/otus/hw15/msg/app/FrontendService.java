package ru.otus.hw15.msg.app;

import ru.otus.hw15.msg.messageSystem.Addressee;

public interface FrontendService extends Addressee {
    void init();

    void createUsers();

    void readRandomUser() throws InterruptedException;

    void getCacheStats();

    void setUserName(String userName);

    void setCacheStats(int hitCount, int missCount);
}

