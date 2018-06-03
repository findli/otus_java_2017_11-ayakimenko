package ru.otus.gc.monitoring;

import com.sun.management.GarbageCollectionNotificationInfo;


public class GCEventInfo {

    private String name;
    private GenerationType generation;
    private long duration;

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

    GenerationType getGeneration() {
        return generation;
    }

    long getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "GenerationType: " + generation + ", GC name = " + name + ", GC duration = " + duration;
    }
}