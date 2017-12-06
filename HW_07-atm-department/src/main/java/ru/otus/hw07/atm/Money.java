package ru.otus.hw07.atm;

public class Money {
    private Denomination denomination;
    private int amount;

    public Money(Denomination denomination, int amount) throws ATMException {
        this.denomination = denomination;
        this.amount = amount;
    }

    public Denomination getDenomination() {
        return denomination;
    }

    public int getAmount() {
        return amount;
    }

    public int getSumm() {
        return this.amount * this.denomination.getValue();
    }
}
