package ru.otus.hw15.msg.app;

import ru.otus.hw15.msg.messageSystem.Address;
import ru.otus.hw15.msg.messageSystem.Addressee;
import ru.otus.hw15.msg.messageSystem.Message;

public abstract class MsgToFrontend extends Message {
    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof FrontendService) {
            exec((FrontendService) addressee);
        } else {
            //todo error!
        }
    }

    public abstract void exec(FrontendService frontendService);
}