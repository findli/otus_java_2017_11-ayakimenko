package ru.otus.L021;

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

    public static void main(String... args) throws InterruptedException {

        System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName());

        MemoryMeter memoryMeter = new MemoryMeter();
        memoryMeter.setUp();

        memoryMeter.measure(Object::new, "Object");
        memoryMeter.clean();

        memoryMeter.measure(String::new, "String with pool");
        memoryMeter.clean();

        memoryMeter.measure(() -> new String(new char[0]), "Empty String pool free");
        memoryMeter.clean();
    }
}
