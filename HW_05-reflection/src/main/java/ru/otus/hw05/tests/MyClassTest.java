package ru.otus.hw05.tests;
import ru.otus.hw05.annotations.*;
import ru.otus.hw05.testframework.ReflectionHelper;

import static ru.otus.hw05.testframework.TestFramework.*;

public class MyClassTest {

    @Before
    public void before() {
        System.out.println("Before test");
    }

    @After
    public void after() {
        System.out.println("After test");
    }

    @Test
    public void testTrue(){
        assertTrue(1 == 2);
    }

    @Test
    public void instantiate() {
        MyClass myClass = ReflectionHelper.instantiate(MyClass.class);
        assertEquals(0, myClass.getA());
        assertEquals(1, ReflectionHelper.instantiate(MyClass.class, 1).getA());
        assertEquals("A", ReflectionHelper.instantiate(MyClass.class, 1, "A").getS());
    }

    @Test
    public void getFieldValue() {
        assertEquals("A", ReflectionHelper.getFieldValue(new MyClass(1, "A"), "s"));

        assertEquals("B", ReflectionHelper.getFieldValue(new MyClass(1, "A"), "s"));
        assertEquals(4, ReflectionHelper.getFieldValue(new MyClass(1, "B"), "a"));
    }

    @Test
    public void setFieldValue() {
        MyClass test = new MyClass(1, "A");
        assertEquals("A", test.getS());
        ReflectionHelper.setFieldValue(test, "s", "B");
        assertEquals("B", test.getS());
    }

    @Test
    public void callMethod() {
        assertEquals("A", ReflectionHelper.callMethod(new MyClass(1, "A"), "getS"));
        MyClass test = new MyClass(1, "A");
        ReflectionHelper.callMethod(test, "setDefault");
        assertEquals("", test.getS());
    }

}
