package ru.otus.hw07.atm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Department {
    private List<ATM> atms = new ArrayList<>();
    private Map<ATM, Memento> history = new HashMap<>();

    public Department(List<ATM> atms) throws ATMException {
        this.atms = atms;
        for (ATM atm : atms) {
            history.put(atm, atm.save());
        }
    }

    public List<ATM> getAtms() {
        return atms;
    }

    public int getAllBalance() throws ATMException {
        int sum = 0;
        for (ATM atm : atms) {
            sum += atm.getBalance();
        }
        return sum;
    }

    public void addAtm(ATM atm) throws ATMException {
        this.atms.add(atm);
        history.put(atm, atm.save());
    }

    public void removeAtm(ATM atm) {
        this.atms.remove(atm);
        history.remove(atm);
    }

    public Map<ATM, List<Money>> withdrawAllATMs() throws ATMException {
        Map<ATM, List<Money>> map = new HashMap();
        for (ATM atm : this.getAtms()) {
            map.put(atm, atm.withdrawAll());
        }
        return map;
    }

    public void restoreAllATMs() {
        for (ATM atm : atms) {
            atm.restore(history.get(atm));
        }
    }

    public void restoreATM(ATM atm) {
        atm.restore(history.get(atm));
    }
}
