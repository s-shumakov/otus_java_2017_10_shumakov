package ru.otus.hw05.tests;
import ru.otus.hw05.annotations.*;
import java.util.Arrays;
import static ru.otus.hw05.testframework.TestFramework.*;

public class MyArrayListTest {
    @Test
    public void add() throws Exception {
        MyArrayList<String> myList = new MyArrayList<>();
        myList.add("A");
        assertEquals(1, myList.size());
    }

    @Test
    public void size() throws Exception {
        MyArrayList<Integer> myList = new MyArrayList<>(Arrays.asList(1,2,3,4,5));
        assertEquals(5, myList.size());
    }

    @Test
    public void get() throws Exception {
        MyArrayList<Integer> myList = new MyArrayList<>(Arrays.asList(1,2,3,4,5));
        assertEquals(4, myList.get(3));
    }

    @Test
    public void set() throws Exception {
        MyArrayList<Integer> myList = new MyArrayList<>(Arrays.asList(1,2,3,4,5));
        myList.set(0, 0);
        assertTrue(0 == myList.get(0));
    }


    @Test
    public void isEmpty() throws Exception {
        MyArrayList<String> myList = new MyArrayList<>();
        assertTrue(0 == myList.size());
    }

    @Test
    public void contains() throws Exception {
        MyArrayList<String> myList = new MyArrayList<>(Arrays.asList("A","B","C"));
        assertTrue(myList.contains("D"));
    }

}