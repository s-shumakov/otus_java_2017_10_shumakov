package ru.otus.hw05;

import ru.otus.hw05.testframework.TestFramework;
import ru.otus.hw05.tests.*;

public class Main{
    public static void main(String... args) {
        TestFramework.runTests("ru.otus.hw05.tests");

        TestFramework.runTests(MyClassTest.class);
        TestFramework.runTests(MyArrayListTest.class);
        TestFramework.runTests(LotteryMachineTest.class);
    }

}
