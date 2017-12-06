package ru.otus.hw06.atm;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ATMTest {
    @Test
    public void deposit() throws ATMException {
        ATM atm = new ATM();
        int balanceBefore = atm.getBalance();
        System.out.println("Balance before: " + balanceBefore);
        for (Denomination d : Denomination.values()) {
            try {
                atm.deposit(new Money(d, 2000));
            } catch (ATMException e) {
                e.printStackTrace();
            }
        }
        int balanceAfter = atm.getBalance();
        System.out.println("Balance after: " + balanceAfter);
        assertTrue(balanceBefore < balanceAfter);
    }

    @Test
    public void withdraw() throws ATMException {
        ATM atm = new ATM();
        atm.deposit(new Money(Denomination.RUB1000, 1));
        atm.deposit(new Money(Denomination.RUB500, 2));
        atm.deposit(new Money(Denomination.RUB100, 2));
        atm.deposit(new Money(Denomination.RUB50, 12));
        System.out.println("Balance: " + atm.getBalance());
        List<Money> money = atm.withdraw(850);
        System.out.println("Balance: " + atm.getBalance());
        money.forEach(m -> System.out.println(m.getDenomination() + " " + m.getAmount()));
    }

    @Test(expected = ATMException.class)
    public void withdrawTooMatch() throws ATMException {
        ATM atm = new ATM();
        atm.deposit(new Money(Denomination.RUB1000, 2));
        atm.withdraw(3000);
    }

    @Test
    public void withdrawAll() throws ATMException {
        ATM atm = new ATM();
        atm.deposit(new Money(Denomination.RUB50, 5));
        atm.deposit(new Money(Denomination.RUB100, 4));
        atm.deposit(new Money(Denomination.RUB500, 3));
        atm.deposit(new Money(Denomination.RUB1000, 2));
        atm.deposit(new Money(Denomination.RUB5000, 1));
        int balance = atm.getBalance();
        System.out.println("Balance: " + balance);
        int cash = atm.withdrawAll().stream().mapToInt(Money::getSumm).sum();
        System.out.println("Cash: " + cash);
        System.out.println("Balance after: " + atm.getBalance());
        assertEquals(balance, cash);
        assertEquals(0, atm.getBalance());
    }

    @Test
    public void getBalance() throws ATMException {
        ATM atm = new ATM();
        atm.deposit(new Money(Denomination.RUB100, 1));
        atm.deposit(new Money(Denomination.RUB500, 1));
        System.out.println("Balance: " + atm.getBalance());
        assertEquals(600, atm.getBalance());
    }

}