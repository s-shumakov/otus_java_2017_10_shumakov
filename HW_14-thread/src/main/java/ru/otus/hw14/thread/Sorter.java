package ru.otus.hw14.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sorter {
    public void parallelSort(int[] array, int threadsCount) throws InterruptedException {
        int startPos;
        int part = array.length / threadsCount;
        List<Thread> threadPool = new ArrayList<>();
        List<int[]> sortedParts = new ArrayList<>();

        for (int i = 0; i < threadsCount; i++) {
            startPos = i * part;
            int[] tmpArr = new int[part];
            System.arraycopy(array, startPos, tmpArr, 0, tmpArr.length);
            Thread thread = new SortThread(tmpArr);
            threadPool.add(thread);
            sortedParts.add(tmpArr);
        }

        threadPool.forEach((t) -> t.start());
        threadPool.forEach((t) -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        int[] result = merge(sortedParts);
        System.arraycopy(result, 0, array, 0, result.length);
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

    public int[] merge(List<int[]> sortedParts) {
        int[] result = sortedParts.get(0);
        for (int i = 1; i < sortedParts.size(); i++){
            result = merge(result, sortedParts.get(i));
        }
        return result;
    }

    public int[] merge(int[] left, int[] right) {
        int i1 = 0;
        int i2 = 0;
        int[] result = new int[left.length + right.length];

        for (int i = 0; i < result.length; i++) {
            if (i2 >= right.length || (i1 < left.length && left[i1] <= right[i2])) {
                result[i] = left[i1];
                i1++;
            } else {
                result[i] = right[i2];
                i2++;
            }
        }
        return result;
    }

}
