package ru.otus.hw02;

public class MemoryMeter {
    public static final int ARRAY_SIZE = 1_000_000;

    public void test (Object obj, int arraySize) throws InterruptedException {
        System.out.println("Starting the loop");
        while (true) {
            Object[] array = new Object[arraySize];
            System.out.println("New array of size: " + array.length + " created");
            for (int i = 0; i < arraySize; i++) {
//                array[i] = new Object();
//                array[i] = new String(""); //String pool
//                array[i] = new String(new char[0]); //without String pool
//                array[i] = new MyClass();
            }
            System.out.println("Created " + arraySize + " objects.");
            Thread.sleep(1000);
        }
    }
    public void getEmptyObjectSize() throws InterruptedException {
        System.out.println("Starting getEmptyObjectSize");
        Runtime runtime = Runtime.getRuntime();
        long memPre, memPost;
//        while (true) {
            System.out.println("Starting loop...");
            System.gc();
            memPre = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("Memory pre: " + memPre);

            Object[] array = new Object[MemoryMeter.ARRAY_SIZE];
            for (int i = 0; i < MemoryMeter.ARRAY_SIZE; i++) {
//                array[i] = new Object();
//                array[i] = new String("");
//                array[i] = new String(new char[0]);
                array[i] = 3434535;
            }
            System.out.println("Created " + MemoryMeter.ARRAY_SIZE + " objects.");
            Thread.sleep(1000);

            memPost = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("Memory post: " + memPost);
            System.out.println("Object size: " + (memPost) / MemoryMeter.ARRAY_SIZE);
//        }
    }
}
