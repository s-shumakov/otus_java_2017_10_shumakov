package ru.otus.hw14.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger log = LogManager.getLogger();
    private static final int ARRAY_SIZE = 40;
    private static final int SIZE = 4;

    public static void main(String[] args) throws Exception {
        Sorter sorter = new Sorter();
        int[] array = sorter.initArray(ARRAY_SIZE);
        sorter.sort(array, SIZE);
    }
}
