package ru.otus.hw15.msg.app;

import ru.otus.hw15.msg.messageSystem.Address;
import ru.otus.hw15.msg.messageSystem.Addressee;
import ru.otus.hw15.msg.messageSystem.Message;

public abstract class MsgToDB extends Message {
    public MsgToDB(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof DBService) {
            exec((DBService) addressee);
        }
    }

    public abstract void exec(DBService dbService);
}
