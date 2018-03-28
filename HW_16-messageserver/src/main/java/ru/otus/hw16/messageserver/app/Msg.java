package ru.otus.hw16.messageserver.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by tully.
 */
public abstract class Msg {
    public static final String CLASS_NAME_VARIABLE = "className";
    protected static final Logger log = LogManager.getLogger();
    private final String from;
    private final String to;
    private final String className;
    private String param;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    protected Msg(String from, String to, Class<?> klass) {
        this.from = from;
        this.to = to;
        this.className = klass.getName();
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
