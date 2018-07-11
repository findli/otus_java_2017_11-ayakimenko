package ru.otus.webserver.db;


import ru.otus.webserver.data.Account;

public interface AccountDbService extends AutoCloseable {

    void save(Account user);

    Account findByLogin(String login);

    void close();
}