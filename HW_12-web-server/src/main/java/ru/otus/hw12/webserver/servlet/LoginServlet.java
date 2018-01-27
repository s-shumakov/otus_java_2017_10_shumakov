package ru.otus.hw12.webserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {

    public static final String LOGIN_PARAMETER_NAME = "login";
    private static final String LOGIN_VARIABLE_NAME = "login";
    private static final String ERROR_VARIABLE_NAME = "error";
    public static final String PASS_PARAMETER_NAME = "pass";
    private static final String LOGIN_PAGE_TEMPLATE = "login.html";
    private static final String ERROR_PAGE_TEMPLATE = "403.html";

    private String login;

    public LoginServlet(String login) {
        this.login = login;
    }

    private static String getLoginPage(String login) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(LOGIN_VARIABLE_NAME, login == null ? "" : login);
        return TemplateProcessor.instance().getPage(LOGIN_PAGE_TEMPLATE, pageVariables);
    }

    private static String getErrorPage(String err) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(ERROR_VARIABLE_NAME, err == null ? "" : err);
        return TemplateProcessor.instance().getPage(ERROR_PAGE_TEMPLATE, pageVariables);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String requestLogin = request.getParameter(LOGIN_PARAMETER_NAME);
        String requestPass = request.getParameter(PASS_PARAMETER_NAME);
        response.setContentType("text/html;charset=utf-8");

        if (requestLogin != null){
            if (requestLogin.equals("admin") && requestPass.equals("admin")) {
                saveToSession(request, LOGIN_PARAMETER_NAME, requestLogin);
                saveToSession(request, PASS_PARAMETER_NAME, requestPass);
                response.sendRedirect("admin");
            } else {
                String page = getErrorPage("Неверный логин или пароль!");
                setOK(response, page);
            }
        } else {
            String page = getLoginPage(login);
            setOK(response, page);
        }
    }

    private void saveToSession(HttpServletRequest request, String paramName, String paramVal) {
        request.getSession().setAttribute(paramName, paramVal);
    }

    private void setOK(HttpServletResponse response, String page) throws IOException {
        response.getWriter().println(page);
        response.setStatus(HttpServletResponse.SC_OK);
    }

}