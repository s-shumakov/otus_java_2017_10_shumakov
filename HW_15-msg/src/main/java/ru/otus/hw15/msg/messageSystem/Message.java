package ru.otus.hw15.msg.messageSystem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Message {
    private final Address from;
    private final Address to;
    protected static final Logger log = LogManager.getLogger();

    public Message(Address from, Address to) {
        this.from = from;
        this.to = to;
    }

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public abstract void exec(Addressee addressee);
}
