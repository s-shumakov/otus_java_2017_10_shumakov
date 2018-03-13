package ru.otus.hw16.frontend;

import ru.otus.hw16.messageserver.channel.SocketMsgWorker;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by tully.
 */
class ClientSocketMsgWorker extends SocketMsgWorker {

    private final Socket socket;

    ClientSocketMsgWorker(String host, int port) throws IOException {
        this(new Socket(host, port));
    }

    private ClientSocketMsgWorker(Socket socket) throws IOException {
        super(socket);
        this.socket = socket;
    }

    public void close() throws IOException {
        super.close();
        socket.close();
    }
}
