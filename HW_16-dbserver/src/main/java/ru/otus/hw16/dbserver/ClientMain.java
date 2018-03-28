package ru.otus.hw16.dbserver;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.hw16.messageserver.app.Msg;
import ru.otus.hw16.messageserver.channel.SocketMsgWorker;
import ru.otus.hw16.messageserver.messages.MsgReadUser;
import ru.otus.hw16.messageserver.messages.MsgReadUserAnswer;
import ru.otus.hw16.messageserver.messages.PingMsg;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tully.
 */
public class ClientMain {
    private static final Logger logger = LogManager.getLogger();

    private static final String HOST = "localhost";
    private static final int PORT = 5050;
    private static final int PAUSE_MS = 5000;
    private static final int MAX_MESSAGES_COUNT = 10;
    private static SocketMsgWorker client;

    public static void main(String[] args) throws Exception {
        start();
        sendMsg();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private static void start() throws Exception {
        client = new ClientSocketMsgWorker(HOST, PORT, "Dbserver client");
        client.init();

        logger.info("Client dbserver start");

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                while (true) {
                    Object msg = client.take();
                    logger.info("Message received log: " + msg.toString());
                }
            } catch (InterruptedException e) {
                logger.warn(e.getMessage());
            }
        });

//        int count = 0;
//        while (count < MAX_MESSAGES_COUNT) {
//            Msg msg = new MsgReadUserAnswer(client.getName(), "Frontend client", "User!!!");
//            client.send(msg);
//            logger.info("Message sent: " + msg.toString());
//            Thread.sleep(PAUSE_MS);
//            count++;
//        }
//        client.close();
//        executorService.shutdown();
    }

    public static void sendMsg(){
        Msg msg = new MsgReadUser(client.getName(), "Frontend client");
        client.send(msg);
        logger.info("Message sent from Dbserver: " + msg.toString());
    }

}
