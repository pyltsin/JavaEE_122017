package ru.otus.dao;

import java.util.List;

public interface DaoCrud<E> {
    E get(long id);

    List<E> getAll();

    void update(E entity);

    void delete(long id);

    void insert(E entity);
}
