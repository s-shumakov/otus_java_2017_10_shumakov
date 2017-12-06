package ru.otus.hw07.atm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Department {
    private List<ATM> atms = new ArrayList<>();

    public Department(List<ATM> atms) {
        this.atms = atms;
    }

    public List<ATM> getAtms() {
        return atms;
    }

    public void setAtms(List<ATM> atms) {
        this.atms = atms;
    }

    public Map<ATM, List<Money>> getAllBalances() {
        return null;
    }
}
