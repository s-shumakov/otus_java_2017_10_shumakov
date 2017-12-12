package ru.otus.hw08.json;

public class Address {
    public String streetAddress;
    public String city;
    public String state;
    public long postalCode;

    public Address(String streetAddress, String city, String state, long postalCode) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }
}
