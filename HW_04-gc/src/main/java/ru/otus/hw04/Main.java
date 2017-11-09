package ru.otus.hw04;

import java.lang.management.ManagementFactory;

public class Main {
    public static void main(String... args) throws Exception {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        int size = 100_000;
        Benchmark mbean = new Benchmark();
        mbean.setSize(size);
        MemoryUtil.startGCMonitor();
        mbean.run();
        MemoryUtil.stopGCMonitor();
    }

}
