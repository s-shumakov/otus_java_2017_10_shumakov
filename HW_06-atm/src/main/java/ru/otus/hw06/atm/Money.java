package ru.otus.hw06.atm;

public class Money {
    public static final int[] DENOMINATIONS = {50, 100, 500, 1000, 5000};
    private int denomination;
    private long amount;

    public Money(int denomination) {
        this.denomination = denomination;
    }

    public Money(int denomination, long amount) {
        this.denomination = denomination;
        this.amount = amount;
    }
}
