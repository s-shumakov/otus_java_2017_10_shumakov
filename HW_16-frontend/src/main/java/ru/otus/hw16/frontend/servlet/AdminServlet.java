package ru.otus.hw16.frontend.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.hw16.messageserver.app.Msg;
import ru.otus.hw16.messageserver.channel.SocketMsgWorker;
import ru.otus.hw16.messageserver.messages.MsgReadUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminServlet extends HttpServlet {
    private static final String DEFAULT_USER_NAME = "UNKNOWN";
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";
    private static final int PERIOD_MS = 5000;
    private static final Logger logger = LogManager.getLogger();
    private SocketMsgWorker client;
//    private Address address;
    private String userName = "test";
    private int hitCount = 0;
    private int missCount = 0;

    public AdminServlet(SocketMsgWorker client) {
        this.client = client;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("AdminServlet doGet " + this);
        response.setContentType("text/html;charset=utf-8");

        if (Authorization.isAdmin(request)) {
//            while (userName == null){
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
            this.sendMsg();

            Map<String, Object> pageVariables = createPageVariablesMap(request);
            response.getWriter().println(TemplateProcessor.instance().getPage(ADMIN_PAGE_TEMPLATE, pageVariables));
            response.setStatus(HttpServletResponse.SC_OK);
//            userName = null;
        } else {
            response.sendRedirect("login");
        }
    }

    private Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("method", request.getMethod());
        pageVariables.put("URL", request.getRequestURL().toString());
        pageVariables.put("locale", request.getLocale());
        pageVariables.put("sessionId", request.getSession().getId());
        pageVariables.put("parameters", request.getParameterMap().toString());
        pageVariables.put("refreshPeriod", String.valueOf(PERIOD_MS));
        pageVariables.put("cacheHits", hitCount);
        pageVariables.put("cacheMisses", missCount);
        pageVariables.put("userName", userName);

        String login = (String) request.getSession().getAttribute("login");
        pageVariables.put("login", login != null ? login : DEFAULT_USER_NAME);
        return pageVariables;
    }

    public void sendMsg(){
        Msg msg = new MsgReadUser(client.getName(), "Frontend client");
        client.send(msg);
        logger.info("Message sent from AdminServlet: " + msg.toString());
    }

}
