package ru.otus.hw06.atm;

import java.util.*;

public class ATM {
    private HashMap<Integer, Cell> cells = new HashMap<>();
    private int balance;

    public static Map<Integer, Integer> denominations = new HashMap<>();

    public ATM() {
        this.initCells();
        balance = 0;
    }

    public void deposit(Money money) throws ATMException {
        if (!Money.DENOMINATIONS.contains(money.getDenomination())) {
            throw new ATMException("Incorrect denomination");
        }
        Cell cell = this.cells.get(money.getDenomination());
        cell.putMoney(money.getAmount());
        this.setBalance();
    }

    public List<Money> withdraw(int expectedAmount) throws ATMException {
        this.checkAvailableAmount(expectedAmount);

        ArrayList<Money> money = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        ArrayList<Integer> list = new ArrayList<>(Money.DENOMINATIONS);
        list.sort(Comparator.reverseOrder());

        for (Integer denomination : list) {
            int required = expectedAmount / denomination;
            int cellAmount = this.cells.get(denomination).getAmount();
            if (required > 0) {
                if (cellAmount >= required) {
                    map.put(denomination, required);
                    expectedAmount -= denomination * required;
                } else {
                    map.put(denomination, cellAmount);
                    expectedAmount -= cellAmount * denomination;
                }
            }
            if (expectedAmount == 0) break;
        }
        if (expectedAmount > 0) {
            throw new ATMException("The requested amount can not be issued");
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            this.cells.get(entry.getKey()).takeMoney(entry.getValue());
            money.add(new Money(entry.getKey(), entry.getValue()));
        }
        this.setBalance();
        return money;
    }

    public List<Money> withdrawAll() throws ATMException {
        ArrayList<Money> money = new ArrayList<>();
        for (Cell cell : this.cells.values()) {
            money.add(new Money(cell.getDenomination(), cell.takeMoney(cell.getAmount())));
        }
        this.setBalance();
        return money;
    }

    public int getBalance() throws ATMException {
        return balance;
    }

    private void setBalance() {
        int balance = 0;
        for (Cell cell : this.cells.values()) {
            balance += cell.getSumm();
        }
        this.balance = balance;
    }

    private void initCells() {
        for (Integer d : Money.DENOMINATIONS) {
            this.cells.put(d, new Cell(d));
        }
    }

    private void checkAvailableAmount(int expectedAmount) throws ATMException {
        if (this.getBalance() < expectedAmount) {
            throw new ATMException("Not enough money in an ATM");
        }
    }

    public void addAmount(int denomination, int count) {
        for (Map.Entry<Integer, Integer> entry : denominations.entrySet()) {
            if (entry.getKey() == denomination) {
                int cash = entry.getValue() + count;
                entry.setValue(cash);
                return;
            }
        }
        denominations.put(denomination, count);
    }
}
