package ru.otus.hw04;

import java.util.ArrayList;

class Benchmark {
//    private volatile int size = 0;
    private int size = 0;

    @SuppressWarnings("InfiniteLoopStatement")
    void run() {
        System.out.println("Starting the loop");
        try {
            ArrayList<String> list = new ArrayList();
            while (true) {

                int local = size;
//                System.out.println("Array of size: " + list.size() + " created");
                for (int i = 0; i < local; i++) {
                    list.add(new String(new char[0]));
                }
                System.out.println("Added " + local + " objects in list");
                System.out.println("List size: " + list.size());

    //            int n = 0;
    //            for (int i = 0; i < list.size(); i++) {
    //                if (i % 100_000 == 0){
    //                    System.out.println(i);
    //                }
    //                if (i % 2 == 0){
    //                    n++;
    //                    list.remove(i);
    //                }
    //            }
    //            int size = list.size();
                list.subList(0, local/2).clear();
    //            System.out.println(size - list.size() + " objects deleted from list");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (OutOfMemoryError error) {
            System.out.println("OutOfMemoryError error!!!!!");
            error.printStackTrace();
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
