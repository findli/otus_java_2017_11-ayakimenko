package ru.otus.gc.monitoring;

public enum GenerationType {
    YOUNG("end of minor GC"),
    OLD("end of major GC");

    private String message;

    GenerationType(String logString) {
        this.message = logString;
    }

    public static GenerationType valueByString(String gcMesage) {
        if (YOUNG.message.equals(gcMesage)) {
            return YOUNG;
        }
        if (OLD.message.equals(gcMesage)) {
            return OLD;
        }
        else return null;
    }
}