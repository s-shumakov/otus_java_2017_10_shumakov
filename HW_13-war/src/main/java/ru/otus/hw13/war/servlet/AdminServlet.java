package ru.otus.hw13.war.servlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.otus.hw13.war.base.DBService;
import ru.otus.hw13.war.cache.CacheEngine;

import javax.servlet.ServletConfig;
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
    private static final int PERIOD_MS = 2000;

    private CacheEngine userCache;
    private DBService dbService;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        this.userCache = (CacheEngine)context.getBean("userCache");
        this.dbService = (DBService)context.getBean("dbService");
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

}
