package ru.otus.hibernate.service;


import ru.otus.hibernate.domain.UserDataSet;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by abyakimenko on 30.03.2018.
 */
public interface DBService extends AutoCloseable {
    void save(UserDataSet user) throws SQLException;

    void save(List<UserDataSet> user) throws SQLException;

    UserDataSet findById(long id);

    UserDataSet findByName(String name);

    String getLocalStatus();

    List<UserDataSet> findAll();
}
