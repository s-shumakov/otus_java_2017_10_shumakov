package ru.otus.hw09.jdbc.base;

public abstract class DataSet {
    private final long id;

    public DataSet(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
