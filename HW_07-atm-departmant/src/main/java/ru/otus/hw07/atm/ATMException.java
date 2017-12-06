package ru.otus.hw07.atm;

public class ATMException extends Exception {

    public ATMException() {
        super();
    }

    public ATMException(String s) {
        super(s);
        System.out.println(s);
    }
}
