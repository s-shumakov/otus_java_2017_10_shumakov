package ru.otus.hw07.atm;

public enum Denomination {
    RUB50(50),
    RUB100(100),
    RUB500(500),
    RUB1000(1000),
    RUB5000(5000);

    private final int value;

    Denomination(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
