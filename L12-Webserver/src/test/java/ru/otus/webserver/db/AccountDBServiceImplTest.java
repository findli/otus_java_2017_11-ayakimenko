package ru.otus.webserver.db;

import org.junit.Test;
import ru.otus.webserver.data.Account;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by abyakimenko on 10.07.2018.
 */
public class AccountDBServiceImplTest {

    @Test
    public void shouldAddAccount() throws SQLException {
        AccountDBService dbService = new AccountDBServiceImpl();
        Account accountDataSet = new Account("log", "pass");

        dbService.save(accountDataSet);

        assertThat(dbService.findByLogin("log").toString(), is(accountDataSet.toString()));
    }
}