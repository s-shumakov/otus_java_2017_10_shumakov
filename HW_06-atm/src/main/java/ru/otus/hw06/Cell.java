package ru.otus.hw06;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    public static final int MAX_AMOUNT = 2500;
    private int denomination;
    private int amount;
    private List<Banknote> banknotes;

    public Cell(int denomination){
        this.denomination = denomination;
        this.amount = 0;
    }

    public Cell(int denomination, List<Banknote> banknotes){
        this.denomination = denomination;
        this.banknotes = banknotes;
        this.amount = this.banknotes.size();
    }

    public void inputBanknotes(List<Banknote> banknotes){
        this.banknotes.addAll(banknotes);
        this.amount = this.banknotes.size();
    }

    public List<Banknote> outBanknotes(int amount){
        this.banknotes.subList(0, amount);
        return null;
    }

    public int getDenomination() {
        return denomination;
    }

    public int getAmount() {
        return amount;
    }
}
