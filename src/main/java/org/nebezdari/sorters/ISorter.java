package org.nebezdari.sorters;

import java.util.Comparator;

public interface ISorter {
    <T> void sort(T[] elements, int length, Comparator<T> comparator);
}
