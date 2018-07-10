package ru.otus.webserver.db;


import ru.otus.webserver.data.Account;

import java.sql.SQLException;

public interface AccountDBService extends AutoCloseable {

    void save(Account user) throws SQLException;

    Account findByLogin(String login) throws SQLException;

    void close();
}