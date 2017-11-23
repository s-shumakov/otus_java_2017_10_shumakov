package ru.otus.hw05.testframework;

import ru.otus.hw05.annotations.After;
import ru.otus.hw05.annotations.Before;
import ru.otus.hw05.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class TestFramework {

    public static void runTests (Class clazz){
        System.out.println("Test class name: " + clazz.getName());
        ArrayList<Method> beforeTests = ReflectionHelper.getAnnotatedMethods(clazz, Before.class);
        ArrayList<Method> tests = ReflectionHelper.getAnnotatedMethods(clazz, Test.class);
        ArrayList<Method> afterTests = ReflectionHelper.getAnnotatedMethods(clazz, After.class);

        for (Method m : tests) {
            Object obj = ReflectionHelper.instantiate(clazz);
            for(Method bm : beforeTests){
                ReflectionHelper.callMethod(obj, bm.getName(), bm.getParameters());
            }
            System.out.println("Test method: "+m.getName());
            ReflectionHelper.callMethod(obj, m.getName(), m.getParameters());

            for(Method am : afterTests){
                ReflectionHelper.callMethod(obj, am.getName(), am.getParameters());
            }
        }
    }

    public static void runTests (String packageName){
        try {
            for (Class clazz : ReflectionHelper.getClasses(packageName)){
                if (!ReflectionHelper.getAnnotatedMethods(clazz, Test.class).isEmpty()) {
                    runTests(clazz);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void assertEquals(Object expected, Object actual) {
        if (!expected.equals(actual)) {
            fail("Expected: " + expected, "Actual: " + actual);
        }
    }

    public static void assertNotEquals(Object unexpected, Object actual) {
        if (unexpected.equals(actual)) {
            fail("Unexpected: " + unexpected, "Actual: " + actual);
        }
    }

    public static void assertTrue(boolean condition) {
        if (!condition) {
            fail("Condition not true");
        }
    }

    public static void assertNull(Object object) {
        if (object != null) {
            fail("Object not null");
        }
    }

    public static void assertNotNull(String message, Object object) {
        if (object == null) {
            fail("Object is null");
        }
    }

    public static void fail(String... args){
        System.out.println("###############");
        System.out.println("# Fail test");
        for (String s : args){
            System.out.println("# " + s);
        }
        System.out.println("###############");
    }

}
