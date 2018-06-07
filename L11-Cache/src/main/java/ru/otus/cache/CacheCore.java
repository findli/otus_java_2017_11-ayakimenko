package ru.otus.cache;

/**
 * Created by abyakimenko on 03.06.2018.
 */
public interface CacheCore<V> {

    void put(String key, V value);

    V get(String key);

    int getHits();

    int getMisses();

    void dispose();

    void getCurrentSize();
}
