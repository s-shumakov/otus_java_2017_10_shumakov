package ru.otus.hw15.msg;


import ru.otus.hw15.msg.app.DBService;
import ru.otus.hw15.msg.app.FrontendService;
import ru.otus.hw15.msg.app.MessageSystemContext;
import ru.otus.hw15.msg.dbService.DBServiceImpl;
import ru.otus.hw15.msg.messageSystem.*;
import ru.otus.hw15.msg.servlet.AdminServlet;

import javax.servlet.ServletConfig;

public class MSMain {
    public static void main(String[] args) throws InterruptedException {
        MessageSystem messageSystem = new MessageSystem();

        MessageSystemContext context = new MessageSystemContext(messageSystem);
        Address frontAddress = new Address("Frontend");
        context.setFrontAddress(frontAddress);
        Address dbAddress = new Address("DB");
        context.setDbAddress(dbAddress);

//        FrontendService frontendService = new FrontendServiceImpl(context, frontAddress);
        FrontendService frontendService = new AdminServlet();
        frontendService.init();

//        DBService dbService = new DBServiceImpl(context, dbAddress);
        DBService dbService = new DBServiceImpl();
        dbService.init();

        messageSystem.start();

        //for test
        frontendService.handleRequest("user1");
        frontendService.handleRequest("user2");

        Thread.sleep(1000);
        messageSystem.dispose();
    }
}
