package ru.otus.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.jdbc.service.DBService;
import ru.otus.jdbc.service.DBServiceImpl;

import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by abyakimenko on 31.03.2018.
 */
public class ExecutorTest {

    private DBService dbService;

    @Before
    public void setUp() {
        dbService = new DBServiceImpl();
        dbService.deleteTables();
        dbService.createTables();
    }

    @After
    public void tearDown() {
        dbService.deleteTables();
    }

    @Test
    public void shouldAddAndLoadUser() throws SQLException {

        UserDataSet user = new UserDataSet(11, "John");
        dbService.save(user);

        UserDataSet loadUser = dbService.load(1);
        assertThat(loadUser.toString(), is(user.toString()));
    }

    @Test
    public void shouldCreateTable() {
        List<String> allTables = dbService.getAllTables();
        assertThat(allTables, hasItem("user_data_set"));
    }

    @Test
    public void shouldDeleteTable() {
        assertThat(dbService.getAllTables(), hasItem("user_data_set"));

        dbService.deleteTables();
        assertThat(dbService.getAllTables(), not(hasItem("user_data_set")));
    }
}