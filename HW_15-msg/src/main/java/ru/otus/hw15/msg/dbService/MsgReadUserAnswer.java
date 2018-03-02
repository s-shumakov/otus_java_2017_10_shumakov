package ru.otus.hw15.msg.dbService;

import ru.otus.hw15.msg.app.FrontendService;
import ru.otus.hw15.msg.app.MsgToFrontend;
import ru.otus.hw15.msg.dataSets.UserDataSet;
import ru.otus.hw15.msg.messageSystem.Address;

public class MsgReadUserAnswer extends MsgToFrontend {
    private final String userName;

    public MsgReadUserAnswer(Address from, Address to, String userName) {
        super(from, to);
        this.userName = userName;
        log.info("MsgReadUserAnswer: " + userName);
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.setUserName(userName);
        log.info("MsgReadUserAnswer exec: " + userName);
    }
}
