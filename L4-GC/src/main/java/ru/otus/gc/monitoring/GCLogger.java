package ru.otus.gc.monitoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.NotificationEmitter;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class GCLogger {

    private static final Logger logger = LoggerFactory.getLogger(GCLogger.class);
    private static Instant timeStart;

    public static void startMonitoring() {
        timeStart = Instant.now();
        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
        //Install a GCListener for each bean
        for (GarbageCollectorMXBean gcBean : gcBeans) {
            NotificationEmitter emitter = (NotificationEmitter) gcBean;
            emitter.addNotificationListener(new GCListener(), null, null);
        }
    }

    public static void printSummaryInfo() {
        long executionTime = Duration.between(timeStart, Instant.now()).getSeconds();
        logger.info("Executing has finished: YOUNG GC: {} times, " +
                        "OLD GC: {} times, " +
                        "total duration: {} ms, " +
                        "Total app live time: {} s",
                GCListener.getYoungCollects(),
                GCListener.getOldCollects(),
                GCListener.getTotalDuration(),
                executionTime);
    }
}
