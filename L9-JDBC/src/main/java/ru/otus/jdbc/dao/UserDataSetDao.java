package ru.otus.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.jdbc.UserDataSet;
import ru.otus.jdbc.executor.Executor;
import ru.otus.jdbc.executor.TResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abyakimenko on 01.04.2018.
 */
public class UserDataSetDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDataSetDao.class);

    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS user_data_set " +
            " (id BIGINT(20) NOT NULL AUTO_INCREMENT, `name` VARCHAR(255) DEFAULT '', age INT(3) DEFAULT 0, " +
            " PRIMARY KEY (ID) ) ENGINE = INNODB DEFAULT CHARSET = utf8;";
    private static final String DELETE_TABLE_USER = "DROP TABLE IF EXISTS user_data_set";
    private static final String SAVE_USER = "INSERT INTO user_data_set (name, age) VALUES ('%s', %d);";
    private static final String FIND_USER_BY_ID = "SELECT * FROM user_data_set WHERE id=";
    private static final String FIND_ALL_USERS = "SELECT * FROM user_data_set";

    private final Executor executor;

    public UserDataSetDao(Connection connection) {
        this.executor = new Executor(connection);
    }

    public void createTable() {
        try {
            executor.executeUpdate(CREATE_TABLE_USER);
            logger.info("Table created");
        } catch (SQLException e) {
            logger.error("Can't create user table", e);
        }
    }

    public void deleteTable() {
        try {
            executor.executeUpdate(DELETE_TABLE_USER);
            logger.info("Table user_data_set was deleted");
        } catch (SQLException e) {
            logger.error("Failed to delete user_data_set table", e);
        }

    }

    public void save(UserDataSet user) {

        final String sql = String.format(SAVE_USER, user.getName(), user.getAge());
        try {
            executor.executeUpdate(sql);
            logger.info("New user was created successfully");
        } catch (SQLException e) {
            logger.error("Can't save user data", e);
        }
    }

    public List<UserDataSet> findAll() {

        List<UserDataSet> list = new ArrayList<>();
        try {
            TResultHandler<List<UserDataSet>> handler = resultSet -> {
                while (resultSet.next()) {
                    list.add(new UserDataSet(resultSet.getLong(1), resultSet.getString(2)));
                }
                return list;
            };
            return executor.executeQuery(FIND_ALL_USERS, handler);
        } catch (SQLException e) {
            logger.error("Can't load all users", e);
            return list;
        }
    }

    public UserDataSet load(long id) {
        try {
            TResultHandler<UserDataSet> handler = resultSet -> {
                resultSet.next();
                return new UserDataSet(resultSet.getLong(1), resultSet.getString(2), resultSet.getLong(3));
            };
            return executor.executeQuery(FIND_USER_BY_ID + id, handler);
        } catch (SQLException e) {
            logger.error("Can't load user with ID={}", id, e);
            return null;
        }
    }
}
