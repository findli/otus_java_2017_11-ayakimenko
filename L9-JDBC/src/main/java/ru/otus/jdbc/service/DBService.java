package ru.otus.jdbc.service;

import ru.otus.jdbc.UserDataSet;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by abyakimenko on 30.03.2018.
 */
public interface DBService {

    void save(UserDataSet user) throws SQLException;

    UserDataSet load(long id);

    void deleteTables();

    void createTables();

    List<String> getAllTables();
}
