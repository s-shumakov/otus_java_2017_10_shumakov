package ru.otus.hw14.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.util.Arrays;

public class SorterTest {
    private static final Logger log = LogManager.getLogger();
    private static final int ARRAY_SIZE = 1_000_000;
    private static final int SIZE = 4;

    @org.junit.Test
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

}