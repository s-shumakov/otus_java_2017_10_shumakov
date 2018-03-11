package ru.otus.hw15.msg.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.hw15.msg.messageSystem.Address;
import ru.otus.hw15.msg.messageSystem.MessageSystem;

import javax.annotation.PreDestroy;

public class MessageSystemContext {
    private static final Logger log = LogManager.getLogger();
    private MessageSystem messageSystem;
    private Address frontAddress;
    private Address dbAddress;

    public MessageSystemContext(){
        log.info("MessageSystemContext()");
    }

    public MessageSystemContext(MessageSystem messageSystem) {
        log.info("MessageSystemContext(MessageSystem messageSystem)");
        this.messageSystem = messageSystem;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public Address getFrontAddress() {
        return frontAddress;
    }

    public void setFrontAddress(Address frontAddress) {
        this.frontAddress = frontAddress;
    }

    public Address getDbAddress() {
        return dbAddress;
    }

    public void setDbAddress(Address dbAddress) {
        this.dbAddress = dbAddress;
    }

    public void setMessageSystem(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    @PreDestroy
    public void dispose() throws Exception {
        log.info("MessageSystemContext.dispose()");
        getMessageSystem().dispose();
    }

}
