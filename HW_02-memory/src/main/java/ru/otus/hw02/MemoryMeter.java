package ru.otus.hw02;

public class MemoryMeter {
    private final int size;

    public MemoryMeter() {
        this.size = 20_000_000;
    }

    public MemoryMeter(int size) {
        this.size = size;
    }

    public void getObjectSize(String name) throws InterruptedException {
        if (name == null || name.isEmpty()){
            name = "EmptyArray";
        }
        System.out.println("Calculation " + name + " size");
        Runtime runtime = Runtime.getRuntime();
        System.gc();
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory before: " + memoryBefore);
        initializeArray(name);
        System.out.println("Created " + this.size + " objects");
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory after: " + memoryAfter);
        System.out.println(name + " size: " + (memoryAfter - memoryBefore) / this.size);
    }

    public void initializeArray(String name) {
        Object[] array = new Object[this.size];
        if (name.equals("EmptyObject")){
            for (int i = 0; i < this.size; i++) {
                array[i] = new Object();
            }
        }else if (name.equals("EmptyString(pool)")){
            for (int i = 0; i < this.size; i++) {
                array[i] = new String("");
            }
        }else if (name.equals("EmptyString(char)")){
            for (int i = 0; i < this.size; i++) {
                array[i] = new String(new char[0]);
            }
        }else if (name.equals("MyClass")){
            for (int i = 0; i < this.size; i++) {
                array[i] = new MyClass();
            }
        }
    }

    class MyClass {
        private int i = 0;
        private long l = 1;
        private boolean b = true;
    }
}
