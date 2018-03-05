package ru.otus.hw14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by abyakimenko on 05.03.2018.
 */
public class MultiThreadSorter {

    private static final Logger logger = LoggerFactory.getLogger(MultiThreadSorter.class);
    // apply merge sort approach (or forkjoin)
    public void sort(int threadsNum, int[] srcArray, int startIndex, int stopIndex) {

        if (threadsNum > 1) {

            final int mid = middlePoint(srcArray, startIndex, stopIndex);

            new Thread(() -> sort(threadsNum - 1, srcArray, startIndex, mid)).start();
            new Thread(() -> sort(threadsNum - 1, srcArray, mid, stopIndex)).start();

        } else {
            Arrays.sort(srcArray, startIndex, stopIndex);
        }
    }

    private int middlePoint(int[] srcArray, int startIndex, int stopIndex) {

        synchronized (MultiThreadSorter.this) {
            logger.info(Thread.currentThread().getName());
            logger.info("startIndex: {}  stopIndex:{}", startIndex, stopIndex);
            return (stopIndex - startIndex) >> 1;
        }
    }
}
