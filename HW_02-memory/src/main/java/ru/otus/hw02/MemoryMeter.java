package ru.otus.hw02;
import java.util.function.Supplier;

public class MemoryMeter {
    private final int size;

    public MemoryMeter() {
        this.size = 20_000_000;
    }

    public void getObjectSize(String name, Supplier<Object> supplier) throws InterruptedException {
        System.out.println("Calculate " + name + " size");
        System.gc();
        Object[] array = new Object[this.size];
        long memoryBefore = getMemory();
        System.out.println("Memory before: " + memoryBefore);
        try {
            initializeArray(supplier, array);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        System.out.println("Created " + this.size + " objects");
        long memoryAfter = getMemory();
        System.out.println("Memory after: " + memoryAfter);
        System.out.println(name + " size: " + (memoryAfter - memoryBefore) / this.size);
    }

    public void initializeArray(Supplier<Object> supplier, Object[] array) throws IllegalAccessException, InstantiationException {
        if (supplier != null) {
            for (int i = 0; i < this.size; i++) {
                array[i] = supplier.get();
            }
        }
    }

    public long getMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

}
