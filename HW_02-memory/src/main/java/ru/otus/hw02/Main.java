package ru.otus.hw02;

import java.lang.management.ManagementFactory;

@SuppressWarnings({"RedundantStringConstructorCall", "InfiniteLoopStatement"})
public class Main {
    public static void main(String... args) throws InterruptedException {
        System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName());
        MemoryMeter mm = new MemoryMeter();
        mm.getObjectSize("");
        mm.getObjectSize("EmptyObject");
        mm.getObjectSize("EmptyString(pool)");
        mm.getObjectSize("EmptyString(char)");
        mm.getObjectSize("MyClass");
    }
}
