package ru.otus.hw08.test;

import java.util.Arrays;
import java.util.Collection;

public class TestClass {
    private String firstName;
    private String lastName;
    private int age;
    private Address address;
    private PhoneNumber[] phoneNumber;
    private int[] arrayInt;
    private String[] arrayString;
    private Collection col;

    public TestClass(String firstName, String lastName, int age, Address address, PhoneNumber[] phoneNumber, int[] arrayInt, String[] arrayString, Collection col) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.arrayInt = arrayInt;
        this.arrayString = arrayString;
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestClass that = (TestClass) o;
        if (!(firstName == null ? that.firstName == null : firstName.equals(that.firstName))) return false;
        if (!(lastName == null ? that.lastName == null : lastName.equals(that.lastName))) return false;
        if (age != that.age) return false;
        if (!Arrays.equals(phoneNumber, that.phoneNumber)) return false;
        if (!Arrays.equals(arrayInt, that.arrayInt)) return false;
        if (!Arrays.equals(arrayString, that.arrayString)) return false;
        if (!col.equals(that.col)) return false;
        return address.equals(that.address);
    }

}
