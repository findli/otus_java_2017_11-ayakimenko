package ru.otus.spring.cache;

import lombok.Getter;
import lombok.Setter;

import java.lang.ref.SoftReference;

/**
 * Created by abyakimenko on 03.06.2018.
 */
@Getter
@Setter
@SuppressWarnings("WeakerAccess")
public class CacheElement<V> {

    private final String key;
    private final SoftReference<V> value;
    private final long creationTime;
    private long lastAccessTime;

    public CacheElement(String key, V value) {
        this.key = key;
        this.value = new SoftReference<>(value);
        this.creationTime = getCurrentTime();
        this.lastAccessTime = getCurrentTime();
    }

    protected long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public V getValue(){
        lastAccessTime = getCurrentTime();
        return value.get();
    }
}
