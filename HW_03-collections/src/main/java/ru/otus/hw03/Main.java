package ru.otus.hw03;

import ru.otus.hw03.ru.otus.hw03.MyArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String... args){
        MyArrayList myStringList = new MyArrayList<String>();
        Collections.addAll(myStringList, "el3","el1","el5","el4","el2");
        printResult("myStringList:", myStringList);

        MyArrayList myIntegerList = new MyArrayList<Integer>(Arrays.asList(6,4,2,8));
        Collections.addAll(myIntegerList, 1,9,7,5,3);
        printResult("myIntegerList:", myIntegerList);

        MyArrayList myStringList2 = new MyArrayList<String>(Arrays.asList("b","a","c"));
        Collections.copy(myStringList, myStringList2);
        printResult("Copy myStringList2 to myStringList:", myStringList);

        Collections.sort(myIntegerList);
        printResult("Sort myIntegerList::", myIntegerList);

        Collections.sort(myStringList);
        printResult("Sort myStringList:", myStringList);
    }

    public static void printResult(String mes, MyArrayList list){
        System.out.println(mes);
        for (Object c : list) {
            System.out.println(c);
        }
    }
}
