package ru.otus.hw14.thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Sorter {
    public void parallelSort(int[] array, int threadsCount) throws InterruptedException {
        int startPos;
        int part = array.length / threadsCount;
        int[] tmpArr = new int[part];

        List<Thread> threadPool = new ArrayList();

        for (int i = 0; i < threadsCount; i++) {
            startPos = i * part;
            Thread thread = new SortThread(array, startPos, tmpArr.length);
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
        Arrays.sort(array);
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
