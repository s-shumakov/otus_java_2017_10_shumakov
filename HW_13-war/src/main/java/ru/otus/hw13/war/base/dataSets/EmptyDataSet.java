package ru.otus.hw13.war.base.dataSets;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EmptyDataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
