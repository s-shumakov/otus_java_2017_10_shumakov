package ru.otus.hw12.webserver.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Authorization {
    public static final String LOGIN_PARAMETER_NAME = "login";
    public static final String PASS_PARAMETER_NAME = "pass";
    public static final String ADMIN_LOGIN = "admin";
    public static final String ADMIN_PASS = "admin";

    public static String getLogin(HttpServletRequest request) {
        return request.getParameter(LOGIN_PARAMETER_NAME);
    }

    public static String getPass(HttpServletRequest request) {
        return request.getParameter(PASS_PARAMETER_NAME);
    }

    public static void saveAdminSession(HttpServletRequest request) {
        String requestLogin = getLogin(request);
        String requestPass = getPass(request);
        request.getSession().setAttribute(LOGIN_PARAMETER_NAME, requestLogin);
        request.getSession().setAttribute(PASS_PARAMETER_NAME, requestPass);
    }

    public static boolean isAdmin(HttpServletRequest request) {
        if (isAdmin(request.getSession())) {
            return true;
        }
        String requestLogin = getLogin(request);
        String requestPass = getPass(request);
        return checkAdmin(requestLogin, requestPass);
    }

    private static boolean isAdmin(HttpSession session) {
        String requestLogin = (String) session.getAttribute(LOGIN_PARAMETER_NAME);
        String requestPass = (String) session.getAttribute(PASS_PARAMETER_NAME);
        return checkAdmin(requestLogin, requestPass);
    }

    private static boolean checkAdmin(String login, String pass){
        if (login != null && login.equals(ADMIN_LOGIN) && pass.equals(ADMIN_PASS)) {
            return true;
        }
        return false;
    }
}
