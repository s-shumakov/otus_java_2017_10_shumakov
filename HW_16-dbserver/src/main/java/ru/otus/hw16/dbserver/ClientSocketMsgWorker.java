package ru.otus.hw16.dbserver;

import ru.otus.hw16.messageserver.channel.SocketMsgWorker;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by tully.
 */
class ClientSocketMsgWorker extends SocketMsgWorker {

    private final Socket socket;
    private final String name;

    ClientSocketMsgWorker(String host, int port, String name) throws IOException {
        this(new Socket(host, port), name);
    }

    private ClientSocketMsgWorker(Socket socket, String name) throws IOException {
        super(socket, name);
        this.socket = socket;
        this.name = name;
    }

    public void close() throws IOException {
        super.close();
        socket.close();
    }
}
