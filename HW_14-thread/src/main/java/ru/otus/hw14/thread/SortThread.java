package ru.otus.hw14.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class SortThread extends Thread {
    private static final Logger log = LogManager.getLogger();
    private int array[];
    private int startPos;
    private int length;

    SortThread(int array[], int startPos, int length){
        this.array = array;
        this.startPos = startPos;
        this.length = length;
    }
    public void run() {
        log.info("start SortThread from: " + startPos + " to " + (startPos + length));
        int[] tmpArr = new int[length];
        System.arraycopy(array, startPos, tmpArr, 0, length);
        Arrays.sort(tmpArr);
        System.arraycopy(tmpArr, 0, array, startPos, length);
        log.info("end SortThread from: " + startPos + " to " + (startPos + length));
    }

}
