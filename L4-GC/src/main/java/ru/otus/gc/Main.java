package ru.otus.gc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.gc.monitoring.GCListener;
import ru.otus.gc.monitoring.GCLogger;

import java.lang.management.ManagementFactory;

/**
 * Created by abyakimenko on 31.05.2018.
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("GC work test, app PID: {}", ManagementFactory.getRuntimeMXBean().getName());
        
        GCLogger.startMonitoring();
        try {
            AppLeaked.buildInstance().startApp();
        } catch (OutOfMemoryError error) {
            logger.error("OOM has occured!", error);
        } finally {
            GCListener.timerStop();
            GCLogger.printSummaryInfo();
        }
    }
}
