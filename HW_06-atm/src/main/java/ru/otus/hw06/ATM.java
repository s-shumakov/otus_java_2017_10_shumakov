package ru.otus.hw06;

import java.util.List;

public class ATM {
    private static final int DEFAULT_SUMM = 1_000_000;
    private int maxSumm;

    public ATM(){
        this.maxSumm = DEFAULT_SUMM;
    }

    public ATM(int maxSumm){
        this.maxSumm = maxSumm;
    }

    public void deposit(List<Banknote> banknotes) throws ATMException{

    }

    public void withdraw(int summ) throws ATMException{

    }

    public void wWithdrawAll() throws ATMException{

    }

    public void getBalance() throws ATMException{

    }
}
