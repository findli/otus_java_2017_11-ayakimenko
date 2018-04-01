package ru.otus.jdbc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.jdbc.ConnectionHelper;
import ru.otus.jdbc.UserDataSet;
import ru.otus.jdbc.dao.UserDataSetDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abyakimenko on 01.04.2018.
 */
public class DBServiceImpl implements DBService {

    private static final Logger logger = LoggerFactory.getLogger(DBServiceImpl.class);
    private final Connection connection;
    private final UserDataSetDao userDao;

    public DBServiceImpl() {
        connection = ConnectionHelper.getConnection();
        userDao = new UserDataSetDao(connection);
    }

    @Override
    public void save(UserDataSet user) {
        userDao.save(user);
    }

    @Override
    public UserDataSet load(long id) {
        return userDao.load(id);
    }

    @Override
    public void deleteTables() {
        userDao.deleteTable();
    }

    @Override
    public void createTables() {
        userDao.createTable();
    }

    @Override
    public List<String> getAllTables() {
        List<String> tablesList = new ArrayList<>();
        String[] types = {"TABLE"};

        try {
            ResultSet resultSet = connection.getMetaData().getTables("jdbc", null, "%", types);
            while (resultSet.next()) {
                tablesList.add(resultSet.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            logger.error("Can't get meta info all tables", e);
        }
        return tablesList;
    }
}
