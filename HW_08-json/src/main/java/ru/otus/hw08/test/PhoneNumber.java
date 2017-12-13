package ru.otus.hw08.test;

public class PhoneNumber {
    private String type;
    private String number;

    public PhoneNumber(String type, String number) {
        this.type = type;
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        if (!(type == null ? that.type == null : type.equals(that.type))) return false;
        return number == null ? that.number == null : number.equals(that.number);
    }
}
