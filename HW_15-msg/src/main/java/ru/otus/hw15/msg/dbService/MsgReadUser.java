package ru.otus.hw15.msg.dbService;

import ru.otus.hw15.msg.app.DBService;
import ru.otus.hw15.msg.app.MsgToDB;
import ru.otus.hw15.msg.dataSets.UserDataSet;
import ru.otus.hw15.msg.messageSystem.Address;

import java.util.Random;

public class MsgReadUser extends MsgToDB {
    public MsgReadUser(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(DBService dbService) {
        Random r = new Random();
        int id = r.nextInt(10);
        while (id == 0){
            id = r.nextInt(10);
        }
        UserDataSet user = dbService.read(id);
        dbService.getMS().sendMessage(new MsgReadUserAnswer(getTo(), getFrom(), user.getName()));
        log.info("MsgReadUser exec: " + user.getName());
    }
}
