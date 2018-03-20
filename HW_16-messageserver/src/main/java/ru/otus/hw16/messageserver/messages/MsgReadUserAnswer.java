package ru.otus.hw16.messageserver.messages;

import ru.otus.hw16.messageserver.app.MsgToFrontend;

public class MsgReadUserAnswer extends MsgToFrontend {
    private final String userName;

    public MsgReadUserAnswer(String from, String to, String userName) {
        super(from, to, MsgReadUserAnswer.class);
        this.userName = userName;
        log.info("MsgReadUserAnswer: " + userName);
    }

//    @Override
//    public void exec(FrontendService frontendService) {
//        frontendService.setUserName(userName);
//        log.info("MsgReadUserAnswer exec: " + userName);
//    }

    @Override
    public void exec() {
        log.info("MsgReadUserAnswer exec: " + userName);
    }

    public void exec(String addressee) {

    }

    @Override
    public String toString() {
        return "MsgReadUserAnswer";
    }
}
