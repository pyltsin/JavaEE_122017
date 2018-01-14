package ru.otus.dao;

import lombok.Getter;

import javax.sql.DataSource;

@Getter
abstract class AbstractDaoCrud<E> implements DaoCrud<E> {
    private DataSource ds;

    public AbstractDaoCrud(DataSource ds) {
        this.ds = ds;
    }
}

