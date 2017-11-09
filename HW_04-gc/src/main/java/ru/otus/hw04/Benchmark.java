package ru.otus.hw04;

import java.util.ArrayList;

class Benchmark {
    private volatile int size = 0;

    @SuppressWarnings("InfiniteLoopStatement")
    void run() {
        long startTime = System.currentTimeMillis();
        System.out.println("Starting the loop");
        ArrayList<String> list = new ArrayList();
        while (true) {
            int local = size;
            for (int i = 0; i < local; i++) {
                list.add(new String(new char[0]));
            }
            list.subList(0, local/2).clear();
            System.out.println("List size: " + list.size());
            System.out.println("Work time (ms): " + (System.currentTimeMillis() - startTime));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
