package ru.otus.hw16.messageserver.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.hw16.messageserver.app.MsgWorker;
import ru.otus.hw16.messageserver.channel.Blocks;
import ru.otus.hw16.messageserver.channel.SocketMsgWorker;
import ru.otus.hw16.messageserver.app.Msg;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tully.
 */
public class MirrorSocketMsgServer implements MirrorSocketMsgServerMBean {
    private static final Logger logger = LogManager.getLogger();

    private static final int THREADS_NUMBER = 1;
    private static final int PORT = 5050;
    private static final int MIRROR_DELAY_MS = 100;

    private final ExecutorService executor;
    private final List<MsgWorker> clients;

    public MirrorSocketMsgServer() {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        clients = new CopyOnWriteArrayList<>();
    }

    @Blocks
    public void start() throws Exception {
//        executor.submit(this::mirror);
        executor.submit(this::sentToClient);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started on port: " + serverSocket.getLocalPort());
            while (!executor.isShutdown()) {
                Socket socket = serverSocket.accept(); //blocks
                SocketMsgWorker client = new SocketMsgWorker(socket, "ServerSocket");
                client.init();
                clients.add(client);
            }
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void sentToClient() {
        while (true) {
            for (MsgWorker client : clients) {
                Msg msg = client.pool();
                while (msg != null) {
                    logger.info("client: " + client.getName() + " " + client);
                    logger.info("Sent message from: " + msg.getFrom() + ", to: " + msg.getTo() + ", message: " + msg.toString());
                    msg.setParam("new value");
                    client.send(msg);
                    msg = client.pool();
                }
            }
            try {
                Thread.sleep(MIRROR_DELAY_MS);
            } catch (InterruptedException e) {
                logger.warn(e.toString());
            }
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void mirror() {
        while (true) {
            for (MsgWorker client : clients) {
                Msg msg = client.pool();
                while (msg != null) {
                    logger.info("Mirroring the message: " + msg.toString());
                    client.send(msg);
                    msg = client.pool();
                }
            }
            try {
                Thread.sleep(MIRROR_DELAY_MS);
            } catch (InterruptedException e) {
                logger.warn(e.toString());
            }
        }
    }

    @Override
    public boolean getRunning() {
        return true;
    }

    @Override
    public void setRunning(boolean running) {
        if (!running) {
            executor.shutdown();
            logger.info("Bye.");
        }
    }
}
