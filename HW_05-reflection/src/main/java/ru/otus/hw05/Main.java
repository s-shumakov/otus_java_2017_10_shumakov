package ru.otus.hw05;

import ru.otus.hw05.annotations.Before;
import ru.otus.hw05.testframework.TestFramework;
import ru.otus.hw05.tests.*;
import ru.otus.hw05.testframework.ReflectionHelper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Main{
    public static void main(String... args) {
//        Package p = Package.getPackage("ru.otus.hw05");
//        Annotation[] a = p.getAnnotations();
        String packageName = "ru.otus.hw05.tests";

        try {
            for (Class clazz : ReflectionHelper.getClasses(packageName)){
                System.out.println(clazz.getName());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println(Package.getPackage("ru.otus.hw05.annotations").getName());
//        Package[] ps = Package.getPackages();
//        Annotation[] a = MyClassTest.class.getPackage().getAnnotations();
//        System.out.println(MyClassTest.class.getPackage().getName());
//        System.out.println(p.getAnnotation(Before.class));


//        TestFramework.runTests(MyClassTest.class);
//        TestFramework.runTests(MyArrayListTest.class);
//        TestFramework.runTests(LotteryMachineTest.class);
    }

}
