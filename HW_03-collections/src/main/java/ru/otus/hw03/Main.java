package ru.otus.hw03;

import ru.otus.hw03.ru.otus.hw03.MyArrayList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String... args){
        MyArrayList myArrayList = new MyArrayList<String>(Arrays.asList("sdf","sdff"));

        ArrayList al = new ArrayList<String>(Arrays.asList("sdf","sdff"));
        Collections.addAll(myArrayList, "qwe","sdf");
        for (Object c : myArrayList) {
            System.out.println(c);
        }
    }
}
