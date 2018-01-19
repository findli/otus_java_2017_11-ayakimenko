package ru.otus.L2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Created by abyakimenko on 29.11.2017.
 * <p>
 * For 64 bit VMs there are options:
 * <p>
 * 1.Using compressed pointers via -XX:+UseCompressedOops (enabled by default on Java 6)
 * In that case: object headers will be 12 bytes, array headers will be 16 bytes (last 4 byte for size of array)
 * <p>
 * 2.Not using compressed pointers via -XX:-UseCompressedOops
 * <p>
 * In that case: object headers will be 16 bytes, array headers will be 20 bytes (last 4 byte for size of array)
 */
public class MemoryMeter {

    private static final Logger logger = LoggerFactory.getLogger(MemoryMeter.class);

    private Object[] array;
    private int size;

    public MemoryMeter() {
        this.size = 1_000_000;
    }

    public MemoryMeter(int size) {
        this.size = size;
        this.array = new Object[size];
    }

    void setUp() throws InterruptedException {
        long memoryChanges = getMemChanges(() -> this.array = new Object[size]);
        logger.info("References size: {}", memoryChanges);
    }

    public void measure(Supplier<Object> supplier, String name) throws InterruptedException {
        long memoryChanges = getMemChanges(() -> {
            for (int i = 0; i < size; i++) {
                array[i] = supplier.get();
            }
        });

        logger.info("### name:{} , size: {}", name, Math.round((double) memoryChanges / size));
    }

    void clean() throws InterruptedException {
        array = new Object[size];
        Runtime.getRuntime().gc();
        TimeUnit.SECONDS.sleep(1); //wait for 1 sec
    }

    private long getMemChanges(Runnable creator) throws InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        TimeUnit.SECONDS.sleep(1); //wait for 1 sec
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        creator.run();
        runtime.gc();
        TimeUnit.SECONDS.sleep(1); //wait for 1 sec
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        return memoryAfter - memoryBefore;
    }
}
