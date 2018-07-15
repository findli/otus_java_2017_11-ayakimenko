package ru.otus.spring.db;


import ru.otus.spring.domain.Account;

import java.sql.SQLException;

public interface AccountDBService extends AutoCloseable {

    void save(Account user) throws SQLException;

    Account findByLogin(String login) throws SQLException;

    void close();
}