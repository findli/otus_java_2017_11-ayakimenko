package ru.otus.hw14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by abyakimenko on 05.03.2018.
 */
public class MultiThreadSorter {

    private static final Logger logger = LoggerFactory.getLogger(MultiThreadSorter.class);

    private final int cores = Runtime.getRuntime().availableProcessors();

    public void mergeSort(int[] srcArray, int threadCount) {

        if (threadCount > 1) {

            int[] left = Arrays.copyOfRange(srcArray, 0, srcArray.length / 2);
            int[] right = Arrays.copyOfRange(srcArray, srcArray.length / 2, srcArray.length);

            Thread lThread = new Thread(() -> mergeSort(left,  threadCount / 2));
            Thread rThread = new Thread(() -> mergeSort(right, threadCount / 2));
            lThread.start();
            rThread.start();

            try {
                lThread.join();
                rThread.join();
            } catch (InterruptedException ie) {}

            // merge them back together
            merge(left, right, srcArray);

        } else {
            Arrays.sort(srcArray);
        }
    }

    /**
     * Сортировка алгоритмом слияния (merge sort)
     *
     * @param array
     */
    private void mergeSortLinear(int[] array) {

        if (array.length >= 2) {

            int middle = array.length / 2;
            int[] left = Arrays.copyOfRange(array, 0, middle);
            int[] right = Arrays.copyOfRange(array, middle, array.length);

            mergeSortLinear(left);
            mergeSortLinear(right);

            // собираем половинки вместе
            merge(left, right, array);
        }
    }

    private void merge(int[] left, int[] right, int[] array) {

        int rightLen = right.length;
        int leftLen = left.length;
        int i1 = 0;
        int i2 = 0;

        for (int i = 0; i < array.length; i++) {

            if (i2 >= rightLen || (i1 < leftLen && left[i1] < right[i2])) {
                array[i] = left[i1];
                ++i1;
            } else {
                array[i] = right[i2];
                ++i2;
            }
        }
    }
}
















