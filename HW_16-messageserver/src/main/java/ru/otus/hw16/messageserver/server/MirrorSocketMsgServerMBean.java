package ru.otus.hw16.messageserver.server;

/**
 * Created by tully.
 */
public interface MirrorSocketMsgServerMBean {
    boolean getRunning();

    void setRunning(boolean running);
}
