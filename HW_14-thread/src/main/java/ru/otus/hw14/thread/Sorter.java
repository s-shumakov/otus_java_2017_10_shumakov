package ru.otus.hw14.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Sorter {
    private static final Logger log = LogManager.getLogger();

    public void parallelSort(int[] array, int threadsCount) throws InterruptedException {
        int startPos;
        int part = array.length / threadsCount;
        int[] sortedArray = new int[array.length];
        int[] tmpArr = new int[part];

        List<Thread> threadPool = new ArrayList();
//        log.info("source Array: " + Arrays.toString(array));

        for (int i = 0; i < threadsCount; i++) {
            startPos = i * part;
            Thread thread = new SortThread(array, sortedArray, startPos, tmpArr.length);
            threadPool.add(thread);
        }

        threadPool.forEach((t) -> t.start());
        threadPool.forEach((t) -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Arrays.sort(sortedArray);
        Arrays.sort(array);

//        log.info("sortedArray: " + Arrays.toString(sortedArray));
//        log.info("array: " + Arrays.toString(array));
    }

    public int[] initArray(int lenght){
        int[] array = new int[lenght];
        Random r = new Random();

        for (int i = 0; i < lenght; i++) {
            int x = r.nextInt(lenght);
            array[i] = x;
        }
        return array;
    }
}
