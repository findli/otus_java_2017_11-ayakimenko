package ru.otus.cache;

import ru.otus.hibernate.domain.DataSet;

;

/**
 * Created by abyakimenko on 09.07.2018.
 */
public class CacheKey {
    private Class aClass;

    public CacheKey(Class aClass) {
        this.aClass = aClass;
    }

    public static String getKey(DataSet set) {
        return new CacheKey(set.getClass()).buildKey(set.getId());
    }

    public String buildKey(long id) {
        return new StringBuilder(aClass.getCanonicalName()).append("-").append(id).toString();
    }
}
