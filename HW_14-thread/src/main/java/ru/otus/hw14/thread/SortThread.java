package ru.otus.hw14.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class SortThread extends Thread {
    private static final Logger log = LogManager.getLogger();
    private final int array[];

    SortThread(int array[]){
        this.array = array;
    }

    public void run() {
        log.info("start SortThread");
        Arrays.sort(array);
        log.info("end SortThread");
    }

}
