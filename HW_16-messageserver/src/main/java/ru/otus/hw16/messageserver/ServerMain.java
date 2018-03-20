package ru.otus.hw16.messageserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.hw16.messageserver.runner.ProcessRunnerImpl;
import ru.otus.hw16.messageserver.server.MirrorSocketMsgServer;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by tully.
 */
public class ServerMain {
    private static final Logger logger = LogManager.getLogger();

    private static final String CLIENT_FRONTEND_START_COMMAND = "java -jar ../HW_16-frontend/target/frontend.jar";
    private static final String CLIENT_DBSERVER_START_COMMAND = "java -jar ../HW_16-dbserver/target/dbserver.jar";
    private static final int CLIENT_START_DELAY_SEC = 5;

    public static void main(String[] args) throws Exception {
        new ServerMain().start();
    }

    private void start() throws Exception {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        startClient(executorService, CLIENT_DBSERVER_START_COMMAND);
        startClient(executorService, CLIENT_FRONTEND_START_COMMAND);

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=Server");
        MirrorSocketMsgServer server = new MirrorSocketMsgServer();
        mbs.registerMBean(server, name);

        server.start();

        executorService.shutdown();
    }

    private void startClient(ScheduledExecutorService executorService, String command) {
        executorService.schedule(() -> {
            try {
                new ProcessRunnerImpl().start(command);
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
        }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);
    }

}
