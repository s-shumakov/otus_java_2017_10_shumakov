package ru.otus.hw06.atm;

import java.util.*;

public class ATM {
    private HashMap<Integer, Cell> cells = new HashMap<>();
    private long balance;

    public ATM(){
        initCells();
        balance = 0;
    }

    public void deposit(Money money) throws ATMException{
        if (!Money.DENOMINATIONS.contains(money.getDenomination())){
            throw new ATMException("Incorrect denomination");
        }
        Cell cell = this.cells.get(money.getDenomination());
        cell.putMoney(money.getAmount());
        setBalance();
    }

    public List<Money> withdraw(long summ) throws ATMException{
        ArrayList<Money> money = new ArrayList<>();
        System.out.println(Money.DENOMINATIONS);
        ArrayList<Integer> denominations = new ArrayList<>(Money.DENOMINATIONS);
        denominations.sort(Comparator.reverseOrder());
        System.out.println(denominations);

        for(Integer d : denominations){
            Cell c = this.cells.get(d);
            while (summ % d > 0){

            }
        }
        return money;
    }

    public List<Money> withdrawAll() throws ATMException{
        ArrayList<Money> money = new ArrayList<>();
        for (Cell cell : this.cells.values()){
            money.add(new Money(cell.getDenomination(), cell.takeMoney(cell.getAmount())));
        }
        setBalance();
        return money;
    }

    public long getBalance() throws ATMException{
        return balance;
    }

    private void setBalance() {
        long balance = 0;
        for (Cell cell : this.cells.values()){
            balance += cell.getSumm();
        }
        this.balance = balance;
    }

    private void initCells(){
        for (Integer d : Money.DENOMINATIONS){
            this.cells.put(d, new Cell(d));
        }
    }
}
