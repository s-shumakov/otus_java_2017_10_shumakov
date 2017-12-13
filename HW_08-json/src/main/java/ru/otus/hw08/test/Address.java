package ru.otus.hw08.test;

public class Address {
    private String streetAddress;
    private String city;
    private String state;
    private long postalCode;

    public Address(String streetAddress, String city, String state, long postalCode) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address that = (Address) o;
        if (!(streetAddress == null ? that.streetAddress == null : streetAddress.equals(that.streetAddress))) return false;
        if (!(city == null ? that.city == null : city.equals(that.city))) return false;
        if (!(state == null ? that.state == null : state.equals(that.state))) return false;
        return postalCode == that.postalCode;
    }
}
