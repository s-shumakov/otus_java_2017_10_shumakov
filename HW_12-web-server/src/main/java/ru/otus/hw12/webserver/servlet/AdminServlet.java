package ru.otus.hw12.webserver.servlet;

import ru.otus.hw12.webserver.base.DBService;
import ru.otus.hw12.webserver.base.dataSets.UserDataSet;
import ru.otus.hw12.webserver.cache.CacheEngine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AdminServlet extends HttpServlet {
    private static final String DEFAULT_USER_NAME = "UNKNOWN";
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";
    private static final String CACHE_HITS = "cacheHits";
    private static final String CACHE_MISSES = "cacheMisses";
    private static final String CACHE_ELEMENTS = "cacheElements";
    private static final String REFRESH_VARIABLE_NAME = "refreshPeriod";
    private static final int PERIOD_MS = 2000;

    private CacheEngine userCache;
    private DBService dbService;

    public AdminServlet(DBService dbService, CacheEngine userCache) {
        this.userCache = userCache;
        this.dbService = dbService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");

        String requestLogin = (String) request.getSession().getAttribute(LoginServlet.LOGIN_PARAMETER_NAME);
        String requestPass = (String) request.getSession().getAttribute(LoginServlet.PASS_PARAMETER_NAME);

        if (requestLogin != null && requestLogin.equals("admin") && requestPass.equals("admin")) {
            readUser();
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
        pageVariables.put(CACHE_HITS, userCache.getHitCount());
        pageVariables.put(CACHE_MISSES, userCache.getMissCount());
        pageVariables.put(CACHE_ELEMENTS, userCache.getElementsCount());
        pageVariables.put(REFRESH_VARIABLE_NAME, String.valueOf(PERIOD_MS));

        String login = (String) request.getSession().getAttribute(LoginServlet.LOGIN_PARAMETER_NAME);
        pageVariables.put("login", login != null ? login : DEFAULT_USER_NAME);
        return pageVariables;
    }

    private void readUser(){
        Random r = new Random();
        int id = r.nextInt(10);
        while (id == 0){
            id = r.nextInt(10);
        }
        UserDataSet user = dbService.read(id);

        System.out.println(user);
        System.out.println("userCache hits: " + userCache.getHitCount());
        System.out.println("userCache misses: " + userCache.getMissCount());
    }

}
