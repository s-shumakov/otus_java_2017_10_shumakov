package ru.otus.hw16.messageserver.app;

import ru.otus.hw16.messageserver.channel.Blocks;

import java.io.IOException;

/**
 * Created by tully.
 */
public interface MsgWorker {
    void send(Msg msg);

    Msg pool();

    @Blocks
    Msg take() throws InterruptedException;

    void close() throws IOException;
}
