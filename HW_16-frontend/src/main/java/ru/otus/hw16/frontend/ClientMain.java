package ru.otus.hw16.frontend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.hw16.frontend.servlet.AdminServlet;
import ru.otus.hw16.frontend.servlet.LoginServlet;
import ru.otus.hw16.messageserver.app.Msg;
import ru.otus.hw16.messageserver.channel.SocketMsgWorker;
import ru.otus.hw16.messageserver.messages.MsgReadUser;

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
    private final static int JETTY_PORT = 8090;
    private final static String PUBLIC_HTML = "public_html";
    private static SocketMsgWorker client;

    public static void main(String[] args) throws Exception {
        startClient();
        startWebServer();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private static void startClient() throws Exception {
        client = new ClientSocketMsgWorker(HOST, PORT, "Frontend client");
        client.init();

        logger.info("Client frontend start");

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
//            Msg msg = new MsgReadUser(client.getName(), "Dbserver client");
//            client.send(msg);
//            logger.info("Message sent: " + msg.toString());
//            Thread.sleep(PAUSE_MS);
//            count++;
//        }
//        client.close();
//        executorService.shutdown();
    }

    private static void startWebServer() throws Exception {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new LoginServlet()), "/login");
        context.addServlet(new ServletHolder(new AdminServlet(client)), "/admin");

        Server server = new Server(JETTY_PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        logger.info("Webserver start");

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                server.start();
                server.join();
            } catch (Exception e) {
                logger.warn(e.getMessage());
            }
        });
    }

}
