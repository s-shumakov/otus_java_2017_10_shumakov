package ru.otus.hw06.atm;

public class Cell {
    public static final int MAX_AMOUNT = 2500;
    private int denomination;
    private int amount;

    public Cell(int denomination) {
        this.denomination = denomination;
        this.amount = 0;
    }

    public int getAmount(){
        return this.amount;
    }

    public int getDenomination() {
        return denomination;
    }

    public void putMoney(int amount) throws ATMException{
        if (this.amount + amount > Cell.MAX_AMOUNT){
            throw new ATMException("Amount of money exceeded");
        }
        this.amount += amount;
    }

    public int takeMoney(int amount) throws ATMException{
        if (this.amount - amount < 0){
            throw new ATMException("Insufficient money");
        }
        this.amount -= amount;
        return amount;
    }

    public long getSumm() {
        return this.amount * this.denomination;
    }

}
