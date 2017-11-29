package ru.otus.hw06.atm;

public class Cell {
    public static final int MAX_AMOUNT = 2500;
    int denomination;
    private long amount;

    public Cell(int denomination) {
        this.denomination = denomination;
        this.amount = 0;
    }
}
