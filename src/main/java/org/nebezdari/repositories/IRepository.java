package org.nebezdari.repositories;


import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

public interface IRepository<T> {
    void add(T item);
    T remove(int index);
    void clear();
    T get(int index);
    T set(int index, T item);
    T[] getAll();
    int size();
    boolean isEmpty();
    void sort(Comparator<T> comparator);
    Optional<T> findFirst(Predicate<T> predicate);
    T[] findAll(Predicate<T> predicate);
}
