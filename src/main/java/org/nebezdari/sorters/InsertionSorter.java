package org.nebezdari.sorters;

import java.util.Comparator;

public class InsertionSorter implements ISorter {
    @Override
    public <T> void sort(T[] elements, int length, Comparator<T> comparator) {
        for (int i = 0; i < length; i++) {
            T current = elements[i];
            int j = i - 1;

            while (j >= 0 && comparator.compare(current, elements[j]) < 0) {
                elements[j + 1] = elements[j];
                j--;
            }

            elements[j + 1] = current;
        }
    }
}
