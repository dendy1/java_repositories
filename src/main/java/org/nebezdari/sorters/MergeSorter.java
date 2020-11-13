package org.nebezdari.sorters;

import java.util.Comparator;

public class MergeSorter implements ISorter {
    @Override
    public <T> void sort(T[] elements, int length, Comparator<T> comparator) {
        if (length < 2) {
            return;
        }

        int mid = length / 2;
        int leftSize = mid;
        int rightSize = length - mid;

        T[] leftArray = (T[]) new Object[leftSize];
        System.arraycopy(elements, 0, leftArray, 0, leftSize);

        T[] rightArray = (T[]) new Object[rightSize];
        System.arraycopy(elements, leftSize, rightArray, 0, rightSize);

        sort(leftArray, leftSize, comparator);
        sort(rightArray, rightSize, comparator);

        merge(elements, leftArray, rightArray, comparator);
    }

    private <T> void merge(T[] elements, T[] leftArray, T[] rightArray, Comparator<T> comparator) {
        int leftIndex = 0;
        int rightIndex = 0;
        int sortedIndex = 0;

        while (leftIndex < leftArray.length && rightIndex < rightArray.length) {
            int comparison = comparator.compare(leftArray[leftIndex], rightArray[rightIndex]);

            if (comparison <= 0) {
                elements[sortedIndex] = leftArray[leftIndex];
                leftIndex++;
            } else {
                elements[sortedIndex] = rightArray[rightIndex];
                rightIndex++;
            }

            sortedIndex++;
        }

        while (leftIndex < leftArray.length) {
            elements[sortedIndex] = leftArray[leftIndex];
            leftIndex++;
            sortedIndex++;
        }

        while (rightIndex < rightArray.length) {
            elements[sortedIndex] = rightArray[rightIndex];
            rightIndex++;
            sortedIndex++;
        }
    }
}
