package org.nebezdari.repositories;


import java.util.Collection;

public interface IRepository<T> {
    void add(T item);
    T remove(int index);
    void clear();
    T get(int index);
    T set(int index, T item);
    T[] getAll();
    int size();
    boolean isEmpty();
}
