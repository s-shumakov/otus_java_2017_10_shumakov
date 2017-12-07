package ru.otus.hw07.atm;

import java.util.HashMap;
import java.util.Map;

public class Memento {
    private Map<Denomination, Integer> savedCells = new HashMap<>();

    public Memento(HashMap<Denomination, Cell> cells) throws ATMException {
        for (Map.Entry<Denomination, Cell> entry : cells.entrySet()) {
            savedCells.put(entry.getKey(), entry.getValue().getAmount());
        }
    }

    public Map<Denomination, Integer> getSavedCells() {
        return savedCells;
    }

}
