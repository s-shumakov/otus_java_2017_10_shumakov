package ru.otus.hw13.war.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {
    private static final String LOGIN_VARIABLE_NAME = "login";
    private static final String ERROR_VARIABLE_NAME = "error";
    private static final String LOGIN_PAGE_TEMPLATE = "login.html";
    private static final String ERROR_PAGE_TEMPLATE = "403.html";

    private String login;

    private String getLoginPage(String login) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(LOGIN_VARIABLE_NAME, login == null ? "" : login);
        return TemplateProcessor.instance().getPage(LOGIN_PAGE_TEMPLATE, pageVariables);
    }

    private String getErrorPage(String err) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(ERROR_VARIABLE_NAME, err == null ? "" : err);
        return TemplateProcessor.instance().getPage(ERROR_PAGE_TEMPLATE, pageVariables);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String page = getLoginPage(login);
        setOK(response, page);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");

        if (Authorization.isAdmin(request)) {
            Authorization.saveAdminSession(request);
            response.sendRedirect("admin");
        } else {
            String page = getErrorPage("Неверный логин или пароль!");
            setOK(response, page);
        }
    }

    private void setOK(HttpServletResponse response, String page) throws IOException {
        response.getWriter().println(page);
        response.setStatus(HttpServletResponse.SC_OK);
    }

}