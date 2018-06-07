package ru.otus.gc.monitoring;

import com.sun.management.GarbageCollectionNotificationInfo;
import lombok.Getter;
import lombok.Setter;


public class GCEventInfo {

    private String name;
    @Getter
    private GenerationType generation;
    @Getter
    private long duration;
    @Getter
    @Setter
    private int count;

    private GCEventInfo() {
    }

    static GCEventInfo buildFromNotificationInfo(GarbageCollectionNotificationInfo notificationInfo) {
        return buildInstance()
                .setGeneration(GenerationType.valueByString(notificationInfo.getGcAction()))
                .setName(notificationInfo.getGcName())
                .setDuration(notificationInfo.getGcInfo().getDuration());
    }

    private static GCEventInfo buildInstance() {
        return new GCEventInfo();
    }

    private GCEventInfo setName(String name) {
        this.name = name;
        return this;
    }

    private GCEventInfo setGeneration(GenerationType generation) {
        this.generation = generation;
        return this;
    }

    private GCEventInfo setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public String toString() {
        return "GenerationType: " + generation + ", GC name = " + name + ", GC duration = " + duration;
    }

    public String logInfo() {
        return "GenerationType: " + generation + ", GC name = " + name + ", GC duration = " + duration + ", count = " + count;
    }

    public void increaseData(long duration) {
        this.duration += duration;
        ++count;
    }
}