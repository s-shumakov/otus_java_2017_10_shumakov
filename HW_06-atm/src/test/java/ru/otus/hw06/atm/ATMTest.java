package ru.otus.hw06.atm;

import org.junit.Test;

import java.util.Map;

public class ATMTest {
    @Test
    public void deposit() throws Exception {
        ATM atm = new ATM();
        for (Map.Entry<Integer, Cell> entry : atm.cells.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue().denomination);
        }

    }

    @Test
    public void withdraw() throws Exception {
    }

    @Test
    public void withdrawAll() throws Exception {
    }

    @Test
    public void getBalance() throws Exception {
    }

}