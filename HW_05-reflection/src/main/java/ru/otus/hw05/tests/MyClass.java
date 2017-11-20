package ru.otus.hw05.tests;

public class MyClass {
    private int a = 0;
    private String s = "";

    public MyClass() {
    }

    public MyClass(Integer a) {
        this.a = a;
    }

    public MyClass(Integer a, String s) {
        this.a = a;
        this.s = s;
    }

    int getA() {
        return a;
    }

    String getS() {
        return s;
    }

    private void setDefault(){
        a = 0;
        s = "";
    }
}
