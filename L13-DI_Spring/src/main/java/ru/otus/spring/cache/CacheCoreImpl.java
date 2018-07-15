package ru.otus.spring.cache;

import java.util.*;
import java.util.function.Function;

/**
 * Created by abyakimenko on 03.06.2018.
 */
public class CacheCoreImpl<V> implements CacheCore<V> {

    private static final int TIME_THRESHOLD = 5;

    private final int maxElementsInCache;
    private final long lifeTime;
    private final long idleTime;
    private final boolean isEternal;

    private int hit = 0;
    private int miss = 0;

    private final Timer timer = new Timer();
    private final Map<String, CacheElement<V>> elements = new LinkedHashMap<>();

    public CacheCoreImpl() {
        this(15, 0, 0, true);
    }

    public CacheCoreImpl(int maxElementsInCache, long lifeTime, long idleTime, boolean isEternal) {
        this.maxElementsInCache = maxElementsInCache;
        this.lifeTime = lifeTime;
        this.idleTime = idleTime;
        this.isEternal = isEternal;
    }

    @Override
    public void put(String key, V value) {
        CacheElement<V> element = new CacheElement<>(key, value);

        if (elements.size() == maxElementsInCache) {
            String firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }

        elements.put(key, element);

        if (!isEternal) {
            if (lifeTime != 0) {
                TimerTask lifeTimerTask = getTimerTask(key, liveElement -> liveElement.getCreationTime() + lifeTime);
                timer.schedule(lifeTimerTask, lifeTime);
            }
            if (idleTime != 0) {
                TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTime);
                timer.schedule(idleTimerTask, idleTime, idleTime);
            }
        }
    }

    @Override
    public V get(String key) {
        CacheElement<V> cacheElement = elements.get(key);
        V cacheElementValue;
        try {
            cacheElementValue = cacheElement.getValue();
        } catch (NullPointerException e) {
            miss++;
            elements.remove(key);
            return null;
        }

        if (Objects.nonNull(cacheElement) && Objects.nonNull(cacheElementValue)) {
            ++hit;
        }
        return cacheElementValue;
    }

    @Override
    public int getHits() {
        return hit;
    }

    @Override
    public int getMisses() {
        return miss;
    }

    @Override
    public int getCurrentSize() {
        return elements.size();
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    // *** PRIVATE
    private TimerTask getTimerTask(final String key, Function<CacheElement<V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                CacheElement<V> element = elements.get(key);
                if (Objects.isNull(element) || isT1BeforeT2(timeFunction.apply(element), System.currentTimeMillis())) {
                    elements.remove(key);
                    this.cancel();
                }
            }
        };
    }

    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD;
    }
}
