package ru.otus.L021;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Created by abyakimenko on 29.11.2017.
 */
public class MemoryMeter {

    private Object[] array;
    private int size;

    public MemoryMeter() {
        this.size = 20_000_000;
    }

    public MemoryMeter(int size) {
        this.size = size;
        this.array = new Object[size];
    }

    void setUp() throws InterruptedException {
        long memoryChanges = getMemChanges(() -> this.array = new Object[size]);
        System.out.println("References size: " + memoryChanges);
    }

    public void measure(Supplier<Object> supplier, String name) throws InterruptedException {
        long memoryChanges = getMemChanges(() -> {
            int i = 0;
            while (i < size) {
                array[i] = supplier.get();
                ++i;
            }
        });

        System.out.println("### name:" + name + ", size: " + Math.round((double) memoryChanges / size));
    }

    void clean() {
        array = new Object[size];
        System.gc();
    }

    private long getMemChanges(Runnable creator) throws InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        System.gc();
        TimeUnit.SECONDS.sleep(1); //wait for 1 sec
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        creator.run();
        System.gc();
        TimeUnit.SECONDS.sleep(1); //wait for 1 sec
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        return memoryAfter - memoryBefore;
    }
}
