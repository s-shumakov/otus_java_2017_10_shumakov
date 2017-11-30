package ru.otus.hw06.atm;

import org.junit.Test;
import static org.junit.Assert.*;

public class ATMTest {
    @Test
    public void deposit() throws ATMException {
        ATM atm = new ATM();
        long balanceBefore = atm.getBalance();
        System.out.println("Balance before: " + balanceBefore);
        for(int d : Money.DENOMINATIONS){
            try {
                atm.deposit(new Money(d, 2000));
            } catch (ATMException e) {
                e.printStackTrace();
            }
        }
        long balanceAfter = atm.getBalance();
        System.out.println("Balance after: " + balanceAfter);
        assertTrue( balanceBefore < balanceAfter);
    }

    @Test(expected = ATMException.class)
    public void wrongDeposit() throws ATMException {
        ATM atm = new ATM();
        atm.deposit(new Money(123, 1));
    }

    @Test
    public void withdraw() throws ATMException {
        ATM atm = new ATM();
        atm.withdraw(1);
    }

    @Test
    public void withdrawAll() throws ATMException {
        ATM atm = new ATM();
        atm.deposit(new Money(50, 5));
        atm.deposit(new Money(100, 4));
        atm.deposit(new Money(500, 3));
        atm.deposit(new Money(1000, 2));
        atm.deposit(new Money(5000, 1));
        long balance = atm.getBalance();
        System.out.println("Balance: " + balance);
        long cash = atm.withdrawAll().stream().mapToLong(Money::getSumm).sum();
        System.out.println("Cash: " + cash);
        System.out.println("Balance after: " + atm.getBalance());
        assertEquals(balance, cash);
        assertEquals(0, atm.getBalance());
    }

    @Test
    public void getBalance() throws ATMException {
        ATM atm = new ATM();
        atm.deposit(new Money(100, 1));
        atm.deposit(new Money(500, 1));
        System.out.println("Balance: " + atm.getBalance());
        assertEquals(600, atm.getBalance());
    }


}