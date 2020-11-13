package org.nebezdari.sorters;

import java.util.Comparator;

public class SelectionSorter implements ISorter {
    @Override
    public <T> void sort(T[] elements, int length, Comparator<T> comparator) {
        for (int i = 0; i < length; i++) {
            int minIndex = i;

            for (int j = i + 1; j < length; j++) {
                if (comparator.compare(elements[j], elements[minIndex]) < 0) {
                    minIndex = j;
                }
            }

            T tmp = elements[i];
            elements[i] = elements[minIndex];
            elements[minIndex] = tmp;
        }
    }
}
