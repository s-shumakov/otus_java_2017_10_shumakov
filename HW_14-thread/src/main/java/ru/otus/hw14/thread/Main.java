package ru.otus.hw14.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.plugin.javascript.navig.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    private static final Logger log = LogManager.getLogger();
    private static final int ARRAY_SIZE = 10000000;
    private static final int SIZE = 4;
    private static final List<Thread> threadPool = new ArrayList();

    public static void main(String[] args) throws Exception {
        int array[] = new int[ARRAY_SIZE];
        Random r = new Random();

        for (int i = 0; i < ARRAY_SIZE; i++) {
            int x = r.nextInt(ARRAY_SIZE);
            array[i] = x;
        }

        int[] sortedArray = new int[ARRAY_SIZE];

//        log.info(Arrays.toString(array));
        int startPos = 0;
        int endPos = 0;
        int part = ARRAY_SIZE/SIZE;

        long startTime = System.nanoTime();
        Arrays.sort(array);
        long endTime = System.nanoTime();
        log.info("sort unsorted array time: " + (endTime - startTime)/1000000 + "ms");
        log.info("array length: " + array.length);
//        log.info(Arrays.toString(array));

        startTime = System.nanoTime();
        for (int i = 0; i < SIZE; i++){
            int[] tmpArr = new int[part];
            startPos = i * part;
            endPos = (i+1) * part - 1;
//            log.info("tmpArr.length: " +tmpArr.length);
//            int finalStartPos = startPos;
//            int finalEndPos = endPos;
            Thread thread = new SortThread(array, tmpArr, startPos, endPos);
            thread.start();
            threadPool.add(thread);

//            Thread.sleep(1000);
//            new Thread(() -> {
//                sort(array, tmpArr, finalStartPos, finalEndPos);
//            }).start();

            System.arraycopy(tmpArr, 0, sortedArray, i * part, part);
        }
        threadPool.forEach((t) -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Arrays.sort(array);
        endTime = System.nanoTime();
        log.info("sort sorted array time: " + (endTime - startTime)/1000000 + "ms");
        log.info("sortedArray length: " + sortedArray.length);
//        log.info(Arrays.toString(sortedArray));
    }


}
