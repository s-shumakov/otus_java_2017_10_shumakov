package ru.otus.hw011;

import com.google.common.collect.Lists;

import java.util.*;

/**
 *
 * Example for L01.1
 *
 * To start the application:
 * mvn package
 * java -jar ./target/HW_01.1-maven.jar //no main manifest attribute, in ./target/HW_01.1-maven.jar
 * java -cp ./target/HW_01.1-maven.jar ru.otus.hw011.Main //java.lang.NoClassDefFoundError: com/google/common/collect/Lists
 * java -cp ./target/HW_01.1-maven.jar;C:\Users\vitaly.chibrikov\.m2\repository\com\google\guava\guava\23.0\guava-23.0.jar ru.otus.l0111.Main
 * To unzip the jar:
 * 7z x -oJAR ./target/HW_01.1-maven.jar
 * unzip -d JAR ./target/HW_01.1-maven.jar
 *
 * To build:
 * mvn package
 * mvn clean compile
 * mvn assembly:single
 * mvn clean compile assembly:single
 */
public class Main {
    private static final int MEASURE_COUNT = 1;

    public static void main(String... args) {
        Collection<Integer> example = new ArrayList<>();
        int min = 0;
        int max = 999_999;
        for (int i = min; i < max + 1; i++) {
            example.add(i);
        }

        List<Integer> result = new ArrayList<>();
        Collections.shuffle((List<Integer>)example);
        calcTime(() -> result.addAll(Lists.reverse((List<Integer>)example)));
    }

    private static void calcTime(Runnable runnable) {
        long startTime = System.nanoTime();
        for (int i = 0; i < MEASURE_COUNT; i++)
            runnable.run();
        long finishTime = System.nanoTime();
        long timeNs = (finishTime - startTime) / MEASURE_COUNT;
        System.out.println("Time spent: " + timeNs + "ns (" + timeNs / 1_000_000 + "ms)");
    }
}
