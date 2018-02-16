package ru.otus.hw14.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class SortThread extends Thread {
    private static final Logger log = LogManager.getLogger();
    private int array[];
    private int sortedArray[];
    private int startPos;
    private int length;
    Sorter sorter;

    SortThread(int array[], int sortedArray[], int startPos, int length, Sorter sorter){
        this.array = array;
        this.sortedArray = sortedArray;
        this.startPos = startPos;
        this.length = length;
        this.sorter = sorter;
    }
    public void run() {
        log.info("start SortThread from: " + startPos + " to " + (startPos + length));
        int[] tmpArr = new int[length];
        System.arraycopy(array, startPos, tmpArr, 0, length);
        Arrays.sort(tmpArr);
        System.arraycopy(tmpArr, 0, sortedArray, startPos, length);
        log.info("end SortThread from: " + startPos + " to " + (startPos + length));
    }

}
