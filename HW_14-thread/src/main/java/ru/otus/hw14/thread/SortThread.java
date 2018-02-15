package ru.otus.hw14.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class SortThread extends Thread {
    private static final Logger log = LogManager.getLogger();
    private int array[];
    private int tmpArr[];
    private int startPos;
    private int endPos;

    SortThread(int array[], int tmpArr[], int startPos, int endPos){
        this.array = array;
        this.tmpArr = tmpArr;
        this.startPos = startPos;
        this.endPos = endPos;
    }
    public void run() {
        sort(array, tmpArr, startPos, endPos);
    }

    public static int[] sort(int array[], int newArray[], int startPos, int endPos){
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        int length = endPos - startPos + 1;
        log.info("startPos: " + startPos + " endPos: " + endPos + " length: " + length);
        System.arraycopy(array, startPos, newArray, 0, length);
//        log.info("newArray:");
//        log.info(Arrays.toString(newArray));
        Arrays.sort(newArray);
//        log.info("newArray sorted:" );
//        log.info(Arrays.toString(newArray));
        return newArray;
    }
}
