package ru.otus.hw06.atm;

public class ATMException extends Exception {

    public ATMException() {
        super();
    }

    public ATMException(String s) {
        super(s);
        System.out.println(s);
    }
}
