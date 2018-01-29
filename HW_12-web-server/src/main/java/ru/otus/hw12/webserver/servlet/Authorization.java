package ru.otus.hw12.webserver.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Authorization {
    public static final String LOGIN_PARAMETER_NAME = "login";
    public static final String PASS_PARAMETER_NAME = "pass";

    public static String getLogin(HttpServletRequest request) {
        return request.getParameter(LOGIN_PARAMETER_NAME);
    }

    public static String getPass(HttpServletRequest request) {
        return request.getParameter(PASS_PARAMETER_NAME);
    }

    public static boolean isAdmin(HttpServletRequest request) {
        String requestLogin = getLogin(request);
        String requestPass = getPass(request);
        if (requestLogin != null && requestLogin.equals("admin") && requestPass.equals("admin")) {
            saveToSession(request, LOGIN_PARAMETER_NAME, requestLogin);
            saveToSession(request, PASS_PARAMETER_NAME, requestPass);
            return true;
        }
        return false;
    }

    public static boolean isAdmin(HttpSession session) {
        String requestLogin = (String) session.getAttribute(LOGIN_PARAMETER_NAME);
        String requestPass = (String) session.getAttribute(PASS_PARAMETER_NAME);
        if (requestLogin != null && requestLogin.equals("admin") && requestPass.equals("admin")) {
            return true;
        }
        return false;
    }

    private static void saveToSession(HttpServletRequest request, String paramName, String paramVal) {
        request.getSession().setAttribute(paramName, paramVal);
    }

}
