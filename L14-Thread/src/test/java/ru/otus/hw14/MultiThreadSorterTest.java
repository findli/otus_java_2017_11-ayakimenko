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

        int size = 1000;
        array = new int[size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(size);
        }

        sorter = new MultiThreadSorter();
    }

    @Test
    public void sort() {
        sorter.sort(4, array, 0, array.length);
        assertTrue(array[0] < array[array.length - 1]);
        assertTrue(array[100] < array[array.length - 46]);
        assertTrue(array[30] < array[array.length - 300]);
    }
}