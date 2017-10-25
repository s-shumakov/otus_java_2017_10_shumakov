package ru.otus.hw02;
import java.util.function.Supplier;

public class MemoryMeter {
    private final int size;

    public MemoryMeter() {
        this.size = 20_000_000;
    }

    public void getObjectSize(String name, Supplier<Object> supplier) throws InterruptedException {
        System.out.println("Calculate " + name + " size");
        Runtime runtime = Runtime.getRuntime();
        System.gc();
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory before: " + memoryBefore);
        try {
            initializeArray(supplier);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        System.out.println("Created " + this.size + " objects");
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory after: " + memoryAfter);
        System.out.println(name + " size: " + (memoryAfter - memoryBefore) / this.size);
    }

    public void initializeArray(Supplier<Object> supplier) throws IllegalAccessException, InstantiationException {
        Object[] array = new Object[this.size];
        if (supplier != null) {
            for (int i = 0; i < this.size; i++) {
                array[i] = supplier.get();
            }
        }
    }
}
