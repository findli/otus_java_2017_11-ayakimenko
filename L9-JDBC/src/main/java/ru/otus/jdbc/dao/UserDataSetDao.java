package ru.otus.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.jdbc.UserDataSet;
import ru.otus.jdbc.annotations.OrmColumn;
import ru.otus.jdbc.annotations.OrmTable;
import ru.otus.jdbc.executor.Executor;
import ru.otus.jdbc.executor.TResultHandler;
import ru.otus.jdbc.utils.ReflectionHelper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by abyakimenko on 01.04.2018.
 */
public class UserDataSetDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDataSetDao.class);

    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS user_data_set " +
            " (id BIGINT(20) NOT NULL AUTO_INCREMENT, `name` VARCHAR(255) DEFAULT '', age INT(3) DEFAULT 0, " +
            " PRIMARY KEY (ID) ) ENGINE = INNODB DEFAULT CHARSET = utf8;";
    private static final String DELETE_TABLE_USER = "DROP TABLE IF EXISTS user_data_set";
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

    private <T extends UserDataSet> String constructInsert(T user, List<Field> fieldList) {
        // get all fields
        // get table name
        final OrmTable tableAnnotation = (OrmTable) ReflectionHelper.getAnnotationFromClass(user.getClass(), OrmTable.class);

        if (fieldList.isEmpty()) {
            logger.error("Entity doesn't have OrmColumns. Entity: {}", user);
        }
        if (Objects.isNull(tableAnnotation)) {
            logger.error("Entity doesn't have OrmTable annotation. Entity: {}", user);
        }

        // construct sql
        final StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ");
        sqlBuilder.append(tableAnnotation.name());
        sqlBuilder.append("(");
        for (int i = 0; i < fieldList.size(); i++) {
            sqlBuilder.append(i == 0 ? fieldList.get(i).getName() : "," + fieldList.get(i).getName());
        }
        sqlBuilder.append(") ");
        sqlBuilder.append(" VALUES ");
        sqlBuilder.append("( ");
        for (int i = 0; i < fieldList.size(); i++) {
            sqlBuilder.append(i == 0 ? "?" : ",?");
        }
        sqlBuilder.append(") ");

        return sqlBuilder.toString();
    }

    public <T extends UserDataSet> void save(T user) {

        List<Field> fieldList = ReflectionHelper.getFieldsWithAnnotation(user.getClass(), OrmColumn.class);

        // execute
        try {
            executor.execUpdatePrepared(constructInsert(user, fieldList), statement -> {
                for (int i = 1; i <= fieldList.size(); i++) {
                    statement.setObject(i, ReflectionHelper.getFieldValue(user, fieldList.get(i - 1)));
                }
                statement.execute();
            });
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
