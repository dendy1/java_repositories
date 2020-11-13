package org.nebezdari.sorters;

import java.util.Comparator;

public class BubbleSorter implements ISorter {
    @Override
    public <T> void sort(T[] elements, int length, Comparator<T> comparator) {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                int comparison = comparator.compare(elements[i], elements[j]);

                if (comparison < 0) {
                    T tmp = elements[i];
                    elements[i] = elements[j];
                    elements[j] = tmp;
                }
            }
        }
    }
}
