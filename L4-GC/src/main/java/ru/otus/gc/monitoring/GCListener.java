package ru.otus.gc.monitoring;

import com.sun.management.GarbageCollectionNotificationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by abyakimenko on 03.06.2018.
 */
public class GCListener implements NotificationListener {

    private static final Logger logger = LoggerFactory.getLogger(GCListener.class);

    private static int youngCollects = 0;
    private static int oldCollects = 0;
    private static long totalDuration = 0L;
    private static long timeout = 30_000;
    private static Timer timer = new Timer();
    private static Map<GenerationType, GCEventInfo> gcMap = new HashMap<>();

    @Override
    public void handleNotification(Notification notification, Object handback) {

        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
            GarbageCollectionNotificationInfo notificationInfo =
                    GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

            GCEventInfo eventInfo = GCEventInfo.buildFromNotificationInfo(notificationInfo);
            synchronized (GCListener.class) {
                totalDuration += eventInfo.getDuration();

                switch (eventInfo.getGeneration()) {
                    case OLD:
                        ++oldCollects;
                        gcMap.computeIfAbsent(eventInfo.getGeneration(), count -> eventInfo).increaseCount();
                        break;
                    case YOUNG:
                        ++youngCollects;
                        gcMap.computeIfAbsent(eventInfo.getGeneration(), count -> eventInfo).increaseCount();
                        break;
                    default:
                        logger.info("Unknown generation type: {}", eventInfo.getGeneration());
                        break;
                }
            }
        }
    }

    static int getYoungCollects() {
        return youngCollects;
    }

    static int getOldCollects() {
        return oldCollects;
    }

    static long getTotalDuration() {
        return totalDuration;
    }

    public static void timerStart() {
        timer.scheduleAtFixedRate(onTimeout(), 0, timeout);
    }

    public static void timerStop() {
        timer.cancel();
    }

    private static TimerTask onTimeout() {
        return new TimerTask() {
            @Override
            public void run() {
                // log info per timeout and clear counter
                if (!gcMap.isEmpty()) {
                    logger.info("###########################################");
                    logger.info("Per minute info:");
                    gcMap.values().forEach(info -> logger.info(info.logInfo()));
                    gcMap.clear();
                    logger.info("###########################################");
                }
            }
        };
    }
}
