package ru.otus.hw06.atm;

import java.util.HashMap;

public class ATM {
    HashMap<Integer, Cell> cells = new HashMap<>();

    public ATM(){
        initCells();
    }

    public void deposit(Money money) throws ATMException{

    }

    public Money[] withdraw(long summ) throws ATMException{
        return null;
    }

    public Money[] withdrawAll() throws ATMException{
        return null;
    }

    public long getBalance() throws ATMException{
        return 0;
    }

    private void initCells(){
        for (Integer d : Money.DENOMINATIONS){
            this.cells.put(d, new Cell(d));
        }
    }
}
