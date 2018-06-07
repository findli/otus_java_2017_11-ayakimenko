package ru.otus.cache;

/**
 * Created by abyakimenko on 03.06.2018.
 */
public class CacheCoreImpl<V> implements CacheCore<V> {
    
    @Override
    public void put(String key, V value) {

    }

    @Override
    public V get(String key) {
        return null;
    }

    @Override
    public int getHits() {
        return 0;
    }

    @Override
    public int getMisses() {
        return 0;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void getCurrentSize() {

    }
}
