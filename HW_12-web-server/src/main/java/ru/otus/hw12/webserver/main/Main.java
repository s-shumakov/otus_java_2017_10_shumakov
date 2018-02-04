package ru.otus.hw12.webserver.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.hw12.webserver.base.DBService;
import ru.otus.hw12.webserver.base.dataSets.AddressDataSet;
import ru.otus.hw12.webserver.base.dataSets.PhoneDataSet;
import ru.otus.hw12.webserver.base.dataSets.UserDataSet;
import ru.otus.hw12.webserver.cache.CacheEngine;
import ru.otus.hw12.webserver.cache.CacheEngineImpl;
import ru.otus.hw12.webserver.dbService.DBServiceImpl;
import ru.otus.hw12.webserver.servlet.AdminServlet;
import ru.otus.hw12.webserver.servlet.LoginServlet;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    private final static int PORT = 8090;
    private final static String PUBLIC_HTML = "public_html";

    public static void main(String[] args) throws Exception {
        CacheEngine userCache = new CacheEngineImpl<Long, UserDataSet>(5, 0, 0, true);
        DBService dbService = new DBServiceImpl(userCache);

        addUsers(dbService);
        startReadUsers(dbService);
        startWebServer(dbService, userCache);
    }

    private static void startWebServer(DBService dbService, CacheEngine userCache) throws Exception {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new LoginServlet("anonymous")), "/login");
        context.addServlet(new ServletHolder(new AdminServlet(dbService, userCache)), "/admin");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();
    }

    private static void addUsers(DBService dbService ) {
        Set<PhoneDataSet> phones1 = new HashSet<>();
        Set<PhoneDataSet> phones2 = new HashSet<>();
        Set<PhoneDataSet> phones3 = new HashSet<>();
        phones1.add(new PhoneDataSet("123-456-7890"));
        phones1.add(new PhoneDataSet("321-654-0987"));
        phones2.add(new PhoneDataSet("345-678-1234"));
        phones3.add(new PhoneDataSet("234-345-5678"));
        phones3.add(new PhoneDataSet("321-654-7654"));
        phones3.add(new PhoneDataSet("321-654-5432"));

        dbService.save(new UserDataSet("user1", phones1, new AddressDataSet("1st street")));
        dbService.save(new UserDataSet("user2", phones2, new AddressDataSet("2st street")));
        dbService.save(new UserDataSet("user3", phones3, new AddressDataSet("3st street")));
        dbService.save(new UserDataSet("user4", new PhoneDataSet("2345678"), new AddressDataSet("4st street")));
        dbService.save(new UserDataSet("user5", new PhoneDataSet("7654356"), new AddressDataSet("5st street")));
        dbService.save(new UserDataSet("user6", new PhoneDataSet("5245753"), new AddressDataSet("6st street")));
        dbService.save(new UserDataSet("user7", new PhoneDataSet("8467467"), new AddressDataSet("7st street")));
        dbService.save(new UserDataSet("user8", new PhoneDataSet("2378732"), new AddressDataSet("8st street")));
        dbService.save(new UserDataSet("user9", new PhoneDataSet("6546657"), new AddressDataSet("9st street")));
        dbService.save(new UserDataSet("user10", new PhoneDataSet("6786733"), new AddressDataSet("10st street")));
    }

    private static void startReadUsers(DBService dbService) {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 100; i++){
                Random r = new Random();
                int id = r.nextInt(10);
                while (id == 0){
                    id = r.nextInt(10);
                }
                UserDataSet user = dbService.read(id);
                System.out.println(user);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
