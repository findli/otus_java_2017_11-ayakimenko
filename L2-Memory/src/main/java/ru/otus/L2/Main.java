package ru.otus.L2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;

/**
 * VM options -Xmx512m -Xms512m
 * <p>
 * Runtime runtime = Runtime.getRuntime();
 * long mem = runtime.totalMemory() - runtime.freeMemory();
 * <p>
 * System.gc()
 * <p>
 * jconsole, connect to pid
 */
@SuppressWarnings({"RedundantStringConstructorCall", "InfiniteLoopStatement"})
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) throws InterruptedException {

        logger.info("pid: {}", ManagementFactory.getRuntimeMXBean().getName());

        MemoryMeter memoryMeter = new MemoryMeter();
        memoryMeter.setUp();

        memoryMeter.measure(Object::new, "Bare Object");
        memoryMeter.clean();

        memoryMeter.measure(String::new, "String with pool");
        memoryMeter.clean();

        memoryMeter.measure(() -> new String(new char[10]), "Empty String pool free");
        memoryMeter.clean();
        
        logger.info("Testing with different array size");

        memoryMeter.measure(() -> new int[0], "Int array, 0 elements");
        memoryMeter.clean();

        memoryMeter.measure(() -> new int[10], "Int array, 10 elements");
        memoryMeter.clean();

        memoryMeter.measure(() -> new int[100], "Int array, 100 elements");
        memoryMeter.clean();

        memoryMeter.measure(() -> new int[1000], "Int array, 1000 elements");
        memoryMeter.clean();

        memoryMeter.measure(() -> new int[10_000], "Int array, 10_000 elements");
        memoryMeter.clean();

        // Out of memory
        /*memoryMeter.measure(() -> new int[100_000], "Int array, 100_000 elements");
        memoryMeter.clean();*/
    }
}
