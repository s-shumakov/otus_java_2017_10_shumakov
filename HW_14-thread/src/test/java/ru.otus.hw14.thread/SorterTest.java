package ru.otus.hw14.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SorterTest {
    private static final Logger log = LogManager.getLogger();
    private static final int ARRAY_SIZE = 40_000_000;
    private static final int SIZE = 4;

    @Test
    public void testParallelSort() throws Exception {
        Sorter sorter = new Sorter();
        int[] sourceArray = sorter.initArray(ARRAY_SIZE);
        int[] testArray = new int[ARRAY_SIZE];
        System.arraycopy(sourceArray, 0, testArray, 0, ARRAY_SIZE);

        long startTime = System.nanoTime();
        sorter.parallelSort(testArray, SIZE);
        long endTime = System.nanoTime();
        log.info("Parallel sort time: " + (endTime - startTime)/1000000 + " ms");

        startTime = System.nanoTime();
        Arrays.sort(sourceArray);
        endTime = System.nanoTime();
        log.info("1 thread sort time: " + (endTime - startTime)/1000000 + " ms");

        Assert.assertArrayEquals(sourceArray, testArray);
    }

    @Test
    public void test() {
        Sorter sorter = new Sorter();
        int arr1[] = {2, 4, 5, 7, 8, 10, 12};
        int arr2[] = {3, 17, 26, 50, 67};
        int arr3[] = {21, 23, 24, 26, 28};
        int arr4[] = {30, 45, 67, 89, 90};
//        int arr[] = new int[arr1.length + arr2.length];
        int arr[] = new int[30];

        List<int[]> sortedParts = new ArrayList<>();
        sortedParts.add(arr1);
        sortedParts.add(arr2);
        sortedParts.add(arr3);
        sortedParts.add(arr4);

        int i = 0;
        while (i < sortedParts.size()){
//            sorter.merge(arr, sortedParts.get(i), sortedParts.get(i+1));
            i = i + 2;
        }
//        for (int[] a : sortedParts){
//            sorter.merge(arr, arr, a);
//        }


        System.out.println(Arrays.toString(arr));
    }

}