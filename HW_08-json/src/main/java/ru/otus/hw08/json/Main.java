package ru.otus.hw08.json;

public class Main {
    public static void main(String[] args) {
        TestClass test = new TestClass();
        test.firstName = "John";
        test.lastName = "Smith";
        test.age = 25;
        test.address = new Address("21 2nd Street", "New York", "NY", 10021);
        PhoneNumber[] phones = {new PhoneNumber("home", "212 555-1234"), new PhoneNumber("fax", "646 555-4567")};
        test.phoneNumber = phones;

        JsonBuilder jsonBuilder = new JsonBuilder();
        jsonBuilder.object2Json(test);
    }
}
