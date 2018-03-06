package ru.otus.hw14;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by abyakimenko on 05.03.2018.
 */
public class MultiThreadSorterTest {

    private int[] array;
    private MultiThreadSorter sorter;

    @Before
    public void setUp() {

        int size = 10;
        array = new int[size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(size);
        }

        sorter = new MultiThreadSorter();
    }

    @Test
    public void sort() {
        sorter.mergeSort(array, 4);
        assertTrue(isSorted(array));
    }

    private static boolean isSorted(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] > a[i + 1]) {
                return false;
            }
        }
        return true;
    }
}