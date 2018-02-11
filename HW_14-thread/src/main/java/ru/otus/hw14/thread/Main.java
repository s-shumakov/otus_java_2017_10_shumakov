package ru.otus.hw14.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Random;

public class Main {
    private static final Logger log = LogManager.getLogger();
    private static final int ARRAY_SIZE = 100;

    public static void main(String[] args) throws Exception {
        int array[] = new int[ARRAY_SIZE];
        Random r = new Random();

        for (int i = 0; i < ARRAY_SIZE; i++) {
            int x = r.nextInt(ARRAY_SIZE);
            array[i] = x;
//            log.info("array["+i+"] "+array[i]);
        }
        Arrays.sort(array);

        for (int i = 0; i < array.length; i++) {
//            log.info("sorted array["+i+"] "+array[i]);
        }
        log.info(array.length);

        Thread thread1 = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("Hello from the thread: " + Thread.currentThread().getName());
        });
        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("Hello from the thread: " + Thread.currentThread().getName());
        });

        thread1.start();
        log.info("thread1.start()");
        thread2.start();
        log.info("thread2.start()");

        thread1.join();
        log.info("thread1.join()");
        thread2.join();
        log.info("thread2.join()");

    }
}
