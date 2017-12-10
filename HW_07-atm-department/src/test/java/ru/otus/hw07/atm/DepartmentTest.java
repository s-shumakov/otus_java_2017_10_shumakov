package ru.otus.hw07.atm;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class DepartmentTest {
    private List<ATM> atms = new ArrayList<>();

    @Before
    public void initATMs() throws ATMException {
        ATM atm1 = new ATM();
        atm1.deposit(new Money(Denomination.RUB50, 20));
        atm1.deposit(new Money(Denomination.RUB100, 10));
        atm1.deposit(new Money(Denomination.RUB500, 5));
        atm1.deposit(new Money(Denomination.RUB1000, 2));
        atm1.deposit(new Money(Denomination.RUB5000, 1));
        atms.add(atm1);

        ATM atm2 = new ATM();
        atm2.deposit(new Money(Denomination.RUB50, 30));
        atm2.deposit(new Money(Denomination.RUB100, 20));
        atm2.deposit(new Money(Denomination.RUB500, 10));
        atm2.deposit(new Money(Denomination.RUB1000, 5));
        atm2.deposit(new Money(Denomination.RUB5000, 2));
        atms.add(atm2);

        ATM atm3 = new ATM();
        atm3.deposit(new Money(Denomination.RUB50, 100));
        atm3.deposit(new Money(Denomination.RUB100, 70));
        atm3.deposit(new Money(Denomination.RUB500, 50));
        atm3.deposit(new Money(Denomination.RUB1000, 20));
        atm3.deposit(new Money(Denomination.RUB5000, 10));
        atms.add(atm3);

        int i = 0;
        for (ATM a : this.atms) {
            i++;
            System.out.println("Balance atm" + i + ": " + a.getBalance());
        }
    }

    @Test
    public void withdrawAllATMs() throws Exception {
        Department dep = new Department(this.atms);
        int initBalance = dep.getAllBalance();
        int withdrawAllMoney = 0;
        System.out.println("initBalance: " + initBalance);
        Map<ATM, List<Money>> atmsMoney = dep.withdrawAllATMs();

        for (Map.Entry<ATM, List<Money>> entry : atmsMoney.entrySet()) {
            System.out.println("Withdraw money from ATM: " + entry.getKey());
            entry.getValue().forEach(m -> System.out.println(m.getDenomination() + " " + m.getAmount()));
            withdrawAllMoney += entry.getValue().stream().mapToInt(m -> m.getSumm()).reduce(0, (x, y) -> x + y);

        }
        System.out.println("withdrawAllMoney: " + withdrawAllMoney);
        assertEquals(0, dep.getAtms().get(0).getBalance());
        assertEquals(0, dep.getAtms().get(0).getBalance());
        assertEquals(0, dep.getAtms().get(0).getBalance());
        assertEquals(initBalance, withdrawAllMoney);
    }

    @Test
    public void addATM() throws ATMException {
        Department dep = new Department(this.atms);
        int sizeBefore = dep.getAtms().size();
        ATM atm = new ATM();
        atm.deposit(new Money(Denomination.RUB1000, 20));
        dep.addAtm(atm);
        assertEquals(sizeBefore + 1, dep.getAtms().size());
    }

    @Test
    public void removeATM() throws ATMException {
        Department dep = new Department(this.atms);
        int sizeBefore = dep.getAtms().size();
        dep.removeAtm(dep.getAtms().get(0));
        assertEquals(sizeBefore - 1, dep.getAtms().size());
    }

    @Test
    public void restoreATM() throws ATMException {
        Department dep = new Department(this.atms);
        ATM atm = dep.getAtms().get(0);
        int initBalance = atm.getBalance();
        System.out.println("Balance init: " + atm.getBalance());
        atm.withdraw(5500);
        System.out.println("Balance after withdraw: " + atm.getBalance());
        dep.restoreATM(atm);
        System.out.println("Balance after restore: " + atm.getBalance());
        assertEquals(initBalance, atm.getBalance());
    }

    @Test
    public void restoreAllATMs() throws ATMException {
        Department dep = new Department(this.atms);
        ATM atm1 = dep.getAtms().get(0);
        ATM atm2 = dep.getAtms().get(1);
        ATM atm3 = dep.getAtms().get(2);

        int initBalance1 = atm1.getBalance();
        int initBalance2 = atm2.getBalance();
        int initBalance3 = atm3.getBalance();
        atm1.withdraw(5500);
        System.out.println("Balance atm1 after withdraw: " + atm1.getBalance());
        atm2.withdraw(12750);
        System.out.println("Balance atm2 after withdraw: " + atm2.getBalance());
        atm3.withdraw(32100);
        System.out.println("Balance atm3 after withdraw: " + atm3.getBalance());

        dep.restoreAllATMs();

        System.out.println("Balance after restore: " + atm1.getBalance());
        System.out.println("Balance after restore: " + atm2.getBalance());
        System.out.println("Balance after restore: " + atm3.getBalance());

        assertEquals(initBalance1, atm1.getBalance());
        assertEquals(initBalance2, atm2.getBalance());
        assertEquals(initBalance3, atm3.getBalance());
    }

    @Test
    public void addATMfix() throws ATMException {
        List<ATM> atms = new ArrayList<>();
        atms.add(new ATM());
        Department dep = new Department(atms);
        System.out.println(dep.getAtms().size());
        assertEquals(1, dep.getAtms().size());

        atms.add(new ATM()); // <--- добавление а Department нового ATM, но мимо метода addAtm()
        System.out.println(dep.getAtms().size());
        assertEquals(1, dep.getAtms().size());

        List<ATM> atms2 = dep.getAtms();
        atms2.add(new ATM());
        System.out.println(dep.getAtms().size());
        assertEquals(1, dep.getAtms().size());
    }



}