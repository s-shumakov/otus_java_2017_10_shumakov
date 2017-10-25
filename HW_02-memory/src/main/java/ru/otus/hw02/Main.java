package ru.otus.hw02;

import java.lang.management.ManagementFactory;

@SuppressWarnings({"RedundantStringConstructorCall", "InfiniteLoopStatement"})
public class Main {
    public static void main(String... args) throws InterruptedException {
        System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName());
        MemoryMeter mm = new MemoryMeter();
        mm.getObjectSize("EmptyArray", null);
        mm.getObjectSize("EmptyObject", Object::new);
        mm.getObjectSize("EmptyString(pool)", String::new);
        mm.getObjectSize("EmptyString(char)", () -> new String(new char[0]));
        mm.getObjectSize("MyClass", MyClass::new);
    }
}
