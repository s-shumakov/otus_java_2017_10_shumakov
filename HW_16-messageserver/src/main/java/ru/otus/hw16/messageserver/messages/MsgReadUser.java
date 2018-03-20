package ru.otus.hw16.messageserver.messages;

import ru.otus.hw16.messageserver.app.MsgToDB;

import java.util.Random;

public class MsgReadUser extends MsgToDB {
    public MsgReadUser(String from, String to) {
        super(from, to, MsgReadUser.class);
    }

    public void exec(String addressee) {

    }

//    @Override
//    public void exec(DBService dbService) {
//        Random r = new Random();
//        int id = r.nextInt(10);
//        while (id == 0){
//            id = r.nextInt(10);
//        }
//        UserDataSet user = dbService.read(id);
//        dbService.getMS().sendMessage(new MsgReadUserAnswer(getTo(), getFrom(), user.getName()));
//        log.info("MsgReadUser exec: " + user.getName());
//    }

    @Override
    public void exec() {
        log.info("MsgReadUser exec: ");
    }

    @Override
    public String toString() {
        return "MsgReadUser";
    }
}
