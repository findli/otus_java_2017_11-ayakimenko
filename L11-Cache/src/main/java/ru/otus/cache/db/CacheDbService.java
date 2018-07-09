package ru.otus.cache.db;

import ru.otus.cache.CacheCore;
import ru.otus.cache.CacheKey;
import ru.otus.hibernate.domain.DataSet;
import ru.otus.hibernate.domain.UserDataSet;
import ru.otus.hibernate.service.DBService;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * Created by abyakimenko on 12.06.2018.
 */
public class CacheDbService implements DBService {

    DBService dbService;
    CacheCore<DataSet> cacheCore;

    public CacheDbService(DBService dbService, CacheCore<DataSet> cacheEngine) {
        this.dbService = dbService;
        this.cacheCore = cacheEngine;
    }

    @Override
    public void save(UserDataSet user) throws SQLException {
        dbService.save(user);
        cacheCore.put(CacheKey.getKey(user), user);
    }

    @Override
    public void save(List<UserDataSet> list) throws SQLException {
        for (UserDataSet user : list) {
            save(user);
        }
    }

    @Override
    public UserDataSet findById(long id) {
        DataSet dataSet = cacheCore.get(new CacheKey(UserDataSet.class).buildKey(id));

        if (Objects.isNull(dataSet)) {
            dataSet = dbService.findById(id);
            if (Objects.nonNull(dataSet)) {
                cacheCore.put(CacheKey.getKey(dataSet), dataSet);
            }
        }
        return (UserDataSet) dataSet;
    }

    @Override
    public UserDataSet findByName(String name) {
        return null;
    }

    @Override
    public String getLocalStatus() {
        return dbService.getLocalStatus();
    }

    @Override
    public List<UserDataSet> findAll() {
        return dbService.findAll();
    }

    @Override
    public void close() throws Exception {
        dbService.close();
        cacheCore.dispose();
    }
}
