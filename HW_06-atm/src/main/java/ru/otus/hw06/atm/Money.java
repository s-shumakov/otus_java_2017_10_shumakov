package ru.otus.hw06.atm;

import java.util.Arrays;
import java.util.List;

public class Money {
    public static final List<Integer> DENOMINATIONS = Arrays.asList(50, 100, 500, 1000, 5000);
    private int denomination;
    private int amount;

    public Money(int denomination) {
        this.denomination = denomination;
    }

    public Money(int denomination, int amount) {
        this.denomination = denomination;
        this.amount = amount;
    }

    public int getDenomination() {
        return denomination;
    }

    public int getAmount() {
        return amount;
    }

    public int getSumm() {
        return this.amount * this.denomination;
    }
}
