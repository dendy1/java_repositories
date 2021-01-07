package org.nebezdari.sorters.quicksort;

import org.nebezdari.sorters.ISorter;

import java.util.Comparator;

public class QuickSorter implements ISorter {
    @Override
    public <T> void sort(T[] elements, int length, Comparator<T> comparator) {
       quickSort(elements, 0, length - 1, comparator);
    }

    private <T> void quickSort(T[] elements, int start, int end, Comparator<T> comparator) {
        if (start < end) {
            int partitionIndex = partition(elements, start, end, comparator);

            quickSort(elements, start, partitionIndex - 1, comparator);
            quickSort(elements, partitionIndex + 1, end, comparator);
        }
    }

    private <T> int partition(T[] elements, int start, int end, Comparator<T> comparator) {
        T pivot = elements[end];
        int i = start - 1;

        for (int j = start; j < end; j++) {
            int comparison = comparator.compare(elements[j], pivot);

            if (comparison < 0) {
                i++;

                T tmp = elements[i];
                elements[i] = elements[j];
                elements[j] = tmp;
            }
        }

        T tmp = elements[i + 1];
        elements[i + 1] = elements[end];
        elements[end] = tmp;

        return i + 1;
    }
}
