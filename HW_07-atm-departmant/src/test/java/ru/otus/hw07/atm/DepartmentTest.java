package ru.otus.hw07.atm;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class DepartmentTest {
    @Test
    public void getAllBalances() throws Exception {
        ATM atm1 = new ATM();
        atm1.deposit(new Money(Denomination.RUB50, 20));
        atm1.deposit(new Money(Denomination.RUB100, 10));
        atm1.deposit(new Money(Denomination.RUB500, 2));
        atm1.deposit(new Money(Denomination.RUB1000, 1));
        atm1.deposit(new Money(Denomination.RUB5000, 1));
        System.out.println("Balance atm1: " + atm1.getBalance());

        ATM atm2 = new ATM();
        atm2.deposit(new Money(Denomination.RUB50, 200));
        atm2.deposit(new Money(Denomination.RUB100, 100));
        atm2.deposit(new Money(Denomination.RUB500, 20));
        atm2.deposit(new Money(Denomination.RUB1000, 10));
        atm2.deposit(new Money(Denomination.RUB5000, 10));
        System.out.println("Balance atm2: " + atm2.getBalance());

        Department dep = new Department(Arrays.asList(atm1, atm2));
    }

}