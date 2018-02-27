package ru.otus.hw15.msg.app;

import ru.otus.hw15.msg.messageSystem.Addressee;

public interface FrontendService extends Addressee {
    void init();

    void handleRequest(String login);

    void addUser(int id, String name);
}

