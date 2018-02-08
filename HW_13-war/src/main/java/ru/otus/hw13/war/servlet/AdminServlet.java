package ru.otus.hw13.war.servlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.otus.hw13.war.base.DBService;
import ru.otus.hw13.war.base.dataSets.AddressDataSet;
import ru.otus.hw13.war.base.dataSets.PhoneDataSet;
import ru.otus.hw13.war.base.dataSets.UserDataSet;
import ru.otus.hw13.war.cache.CacheEngine;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class AdminServlet extends HttpServlet {
    private static final String DEFAULT_USER_NAME = "UNKNOWN";
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";
    private static final int PERIOD_MS = 2000;

    private CacheEngine userCache;
    private DBService dbService;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        this.userCache = (CacheEngine)context.getBean("userCache");
        this.dbService = (DBService)context.getBean("dbService");

        addUsers(dbService);
        startReadUsers(dbService);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");

        if (Authorization.isAdmin(request)) {
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
        System.out.println("userCache hits: " + userCache.getHitCount());
        System.out.println("userCache misses: " + userCache.getMissCount());
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
