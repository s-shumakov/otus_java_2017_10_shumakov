package ru.otus.hw15.msg.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.otus.hw15.msg.app.DBService;
import ru.otus.hw15.msg.app.FrontendService;
import ru.otus.hw15.msg.app.MessageSystemContext;
import ru.otus.hw15.msg.dataSets.AddressDataSet;
import ru.otus.hw15.msg.dataSets.PhoneDataSet;
import ru.otus.hw15.msg.dataSets.UserDataSet;
import ru.otus.hw15.msg.cache.CacheEngine;
import ru.otus.hw15.msg.dbService.MsgGetUserId;
import ru.otus.hw15.msg.messageSystem.Address;
import ru.otus.hw15.msg.messageSystem.Message;
import ru.otus.hw15.msg.messageSystem.MessageSystem;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
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
    private Address address;
    private final Map<Integer, String> users = new HashMap<>();
    private static final Logger log = LogManager.getLogger();

    @Inject
    private CacheEngine userCache;
    @Inject
    private DBService dbService;
    @Inject
    private MessageSystemContext context;

    public void init() {
        log.info("AdminServlet init(ServletConfig config)");
//        initFromContext(config);
        initInject();

        address = new Address("Frontend");
        context.setFrontAddress(address);
        context.getMessageSystem().addAddressee(this);

        Address dbAddress = new Address("DB");
        context.setDbAddress(dbAddress);

        dbService.init();
        context.getMessageSystem().start();

        addUsers(dbService);
    }

    private void initFromContext(ServletConfig config) {
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        this.userCache = (CacheEngine)context.getBean("userCache");
        this.dbService = (DBService)context.getBean("dbService");
    }

    private void initInject() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("AdminServlet doGet");
        response.setContentType("text/html;charset=utf-8");

        if (Authorization.isAdmin(request)) {
            handleRequest("user1");
            startReadUsers(dbService);
            getCacheStats();
            Map<String, Object> pageVariables = createPageVariablesMap(request, userCache);

            response.getWriter().println(TemplateProcessor.instance().getPage(ADMIN_PAGE_TEMPLATE, pageVariables));
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.sendRedirect("login");
        }
    }

    private static Map<String, Object> createPageVariablesMap(HttpServletRequest request, CacheEngine userCache) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("method", request.getMethod());
        pageVariables.put("URL", request.getRequestURL().toString());
        pageVariables.put("locale", request.getLocale());
        pageVariables.put("sessionId", request.getSession().getId());
        pageVariables.put("parameters", request.getParameterMap().toString());
        pageVariables.put("cacheHits", userCache.getHitCount());
        pageVariables.put("cacheMisses", userCache.getMissCount());
        pageVariables.put("cacheElements", userCache.getElementsCount());
        pageVariables.put("refreshPeriod", String.valueOf(PERIOD_MS));

        String login = (String) request.getSession().getAttribute("login");
        pageVariables.put("login", login != null ? login : DEFAULT_USER_NAME);
        return pageVariables;
    }

    private void getCacheStats(){
        log.info("userCache hits: " + userCache.getHitCount());
        log.info("userCache misses: " + userCache.getMissCount());
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
            Random r = new Random();
            int id = r.nextInt(10);
            while (id == 0){
                id = r.nextInt(10);
            }
            UserDataSet user = dbService.read(id);
            log.info(user);
        });
        thread.start();
    }

    @Override
    public void handleRequest(String login) {
        Message message = new MsgGetUserId(getAddress(), context.getDbAddress(), login);
        context.getMessageSystem().sendMessage(message);
    }


    @Override
    public void addUser(int id, String name) {
        users.put(id, name);
        log.info("User: " + name + " has id: " + id);
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
