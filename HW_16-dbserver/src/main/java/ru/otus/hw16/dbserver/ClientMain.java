package ru.otus.hw16.dbserver;

import ru.otus.hw16.messageserver.app.Msg;
import ru.otus.hw16.messageserver.channel.SocketMsgWorker;
import ru.otus.hw16.messageserver.messages.PingMsg;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tully.
 */
public class ClientMain {
    private static final Logger logger = Logger.getLogger(ClientMain.class.getName());

    private static final String HOST = "localhost";
    private static final int PORT = 5050;
    private static final int PAUSE_MS = 5000;
    private static final int MAX_MESSAGES_COUNT = 10;


    public static void main(String[] args) throws Exception {
        new ClientMain().start();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void start() throws Exception {
        SocketMsgWorker client = new ClientSocketMsgWorker(HOST, PORT);
        client.init();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                while (true) {
                    Object msg = client.take();
                    logger.log(Level.INFO, "Message received log: " + msg.toString());
                }
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        });

        int count = 0;
        while (count < MAX_MESSAGES_COUNT) {
            Msg msg = new PingMsg();
            client.send(msg);
            logger.log(Level.INFO, "Message sent: " + msg.toString());
            Thread.sleep(PAUSE_MS);
            count++;
        }
        client.close();
        executorService.shutdown();
    }

}
