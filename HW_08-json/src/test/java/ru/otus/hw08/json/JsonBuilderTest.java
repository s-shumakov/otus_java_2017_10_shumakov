package ru.otus.hw08.json;

import com.google.gson.Gson;

import org.junit.Test;
import ru.otus.hw08.test.Address;
import ru.otus.hw08.test.PhoneNumber;
import ru.otus.hw08.test.TestClass;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JsonBuilderTest {
    @Test
    public void object2Json() throws Exception {
        Address address = new Address ("21 2nd Street", "New York", "NY", 10021);
        PhoneNumber[] phones = {new PhoneNumber("home", "212 555-1234"), new PhoneNumber("fax", null)};
        List<String> col = new ArrayList<>();
        col.add("abc");
        col.add("def");
        int[] arrayInt = {1,2,3,4,5};
        String[] arrayString = {"Z", "X", "C"};
        TestClass obj = new TestClass("John", "Smith", 25, address, phones, arrayInt, arrayString, col);

        JsonBuilder jsonBuilder = new JsonBuilder();
        String myJson = jsonBuilder.object2Json(obj);
        System.out.println("myJson: " + myJson);

        Gson gson = new Gson();
        String json = gson.toJson(obj);
        System.out.println("gson  : " + json);

        TestClass obj2 = gson.fromJson(myJson, TestClass.class);
        assertEquals(obj, obj2);
    }
}