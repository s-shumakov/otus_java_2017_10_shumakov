package ru.otus.hw15.msg.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.hw15.msg.app.FrontendService;
import ru.otus.hw15.msg.app.MessageSystemContext;
import ru.otus.hw15.msg.dbService.MsgCreateUsers;
import ru.otus.hw15.msg.dbService.MsgGetCacheStats;
import ru.otus.hw15.msg.dbService.MsgReadUser;
import ru.otus.hw15.msg.messageSystem.Address;
import ru.otus.hw15.msg.messageSystem.Message;
import ru.otus.hw15.msg.messageSystem.MessageSystem;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class AdminServlet extends HttpServlet implements FrontendService{
    private static final String DEFAULT_USER_NAME = "UNKNOWN";
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";
    private static final int PERIOD_MS = 2000;
    private static final Logger log = LogManager.getLogger();
    private Address address;
    private String userName;
    private int hitCount;
    private int missCount;

    @Inject
    private MessageSystemContext context;

    public void init() {
        log.info("AdminServlet init()");
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        address = context.getFrontAddress();
        context.getMessageSystem().addAddressee(this);
        context.getMessageSystem().start();
        createUsers();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("AdminServlet doGet " + this);
        response.setContentType("text/html;charset=utf-8");

        if (Authorization.isAdmin(request)) {
            readRandomUser();
            getCacheStats();
            while (userName == null){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Map<String, Object> pageVariables = createPageVariablesMap(request);
            response.getWriter().println(TemplateProcessor.instance().getPage(ADMIN_PAGE_TEMPLATE, pageVariables));
            response.setStatus(HttpServletResponse.SC_OK);
            userName = null;
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

    @Override
    public void createUsers() {
        Message message = new MsgCreateUsers(getAddress(), context.getDbAddress());
        context.getMessageSystem().sendMessage(message);
    }

    @Override
    public void readRandomUser() {
        log.info("readRandomUser");
        Message message = new MsgReadUser(getAddress(), context.getDbAddress());
        context.getMessageSystem().sendMessage(message);
    }

    @Override
    public void getCacheStats() {
        log.info("getCacheStats");
        Message message = new MsgGetCacheStats(getAddress(), context.getDbAddress());
        context.getMessageSystem().sendMessage(message);
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
        log.info("setUserName: " + userName);
    }

    @Override
    public void setCacheStats(int hitCount, int missCount){
        this.hitCount = hitCount;
        this.missCount = missCount;
        log.info("userCache hits: " + hitCount + ", userCache misses: " + missCount);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }
}
