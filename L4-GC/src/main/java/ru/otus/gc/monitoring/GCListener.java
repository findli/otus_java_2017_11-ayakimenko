package ru.otus.gc.monitoring;

import com.sun.management.GarbageCollectionNotificationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;

/**
 * Created by abyakimenko on 03.06.2018.
 */
public class GCListener implements NotificationListener {

    private static final Logger logger = LoggerFactory.getLogger(GCListener.class);

    private static int youngCollects = 0;
    private static int oldCollects = 0;
    private static long totalDuration = 0L;

    @Override
    public void handleNotification(Notification notification, Object handback) {

        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
            GarbageCollectionNotificationInfo notificationInfo =
                    GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

            GCEventInfo eventInfo = GCEventInfo.buildFromNotificationInfo(notificationInfo);

            logger.info("eventInfo: {}", eventInfo.toString());

            synchronized (GCListener.class) {
                totalDuration += eventInfo.getDuration();

                switch (eventInfo.getGeneration()) {
                    case OLD:
                        ++oldCollects;
                        break;
                    case YOUNG:
                        ++youngCollects;
                        break;
                    default:
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
}
