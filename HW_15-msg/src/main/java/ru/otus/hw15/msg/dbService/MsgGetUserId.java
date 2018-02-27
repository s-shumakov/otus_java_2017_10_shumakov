package ru.otus.hw15.msg.dbService;

import ru.otus.hw15.msg.app.DBService;
import ru.otus.hw15.msg.app.MsgToDB;
import ru.otus.hw15.msg.dataSets.UserDataSet;
import ru.otus.hw15.msg.messageSystem.Address;

public class MsgGetUserId extends MsgToDB {
    private final String login;

    public MsgGetUserId(Address from, Address to, String login) {
        super(from, to);
        this.login = login;
    }

    @Override
    public void exec(DBService dbService) {
        UserDataSet user = dbService.readByName(login);
        dbService.getMS().sendMessage(new MsgGetUserIdAnswer(getTo(), getFrom(), login, (int)user.getId()));
    }
}
