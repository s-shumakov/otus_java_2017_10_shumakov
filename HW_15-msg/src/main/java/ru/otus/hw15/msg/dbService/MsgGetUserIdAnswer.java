package ru.otus.hw15.msg.dbService;

import ru.otus.hw15.msg.app.FrontendService;
import ru.otus.hw15.msg.app.MsgToFrontend;
import ru.otus.hw15.msg.messageSystem.Address;

public class MsgGetUserIdAnswer extends MsgToFrontend {
    private final String name;
    private final int id;

    public MsgGetUserIdAnswer(Address from, Address to, String name, int id) {
        super(from, to);
        this.name = name;
        this.id = id;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.addUser(id, name);
    }
}
