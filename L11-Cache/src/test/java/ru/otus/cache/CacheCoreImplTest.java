package ru.otus.cache;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.cache.db.CacheDbService;
import ru.otus.hibernate.domain.DataSet;
import ru.otus.hibernate.domain.UserDataSet;
import ru.otus.hibernate.service.DBService;
import ru.otus.hibernate.service.DBServiceImpl;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Created by abyakimenko on 09.07.2018.
 */
public class CacheCoreImplTest {

    private DBService dbService;
    private CacheCore<DataSet> cacheEngine;

    @Before
    public void setUp() {
        cacheEngine = new CacheCoreImpl<>(10, 5000, 0, false);
        dbService = new CacheDbService(new DBServiceImpl(), cacheEngine);
    }

    @After
    public void tearDown() throws Exception {
        dbService.close();
    }

    @Test
    public void shouldNotIncrementHitMiss() throws SQLException {
        UserDataSet user1 = new UserDataSet("name1", 30);
        UserDataSet user2 = new UserDataSet("name2", 33);

        dbService.save(user1);
        dbService.save(user2);

        assertThat(cacheEngine.getCurrentSize(), is(2));

        assertThat(cacheEngine.getMisses(), is(0));
        assertThat(cacheEngine.getHits(), is(0));
    }

    @Test
    public void shouldIncrementHit() throws SQLException {
        UserDataSet user = new UserDataSet("name1", 30);
        UserDataSet user2 = new UserDataSet("name2", 33);

        dbService.save(user);
        dbService.save(user2);

        assertThat(cacheEngine.getCurrentSize(), is(2));

        dbService.findById(user.getId());
        dbService.findById(user.getId());
        dbService.findById(user2.getId());
        dbService.findById(user2.getId());

        assertThat(cacheEngine.getMisses(), is(0));
        assertThat(cacheEngine.getHits(), is(4));
    }

    @Test
    public void shouldIncrementMiss() throws SQLException, InterruptedException {
        UserDataSet user1 = new UserDataSet("name1", 30);
        UserDataSet user2 = new UserDataSet("name2", 33);

        dbService.save(user1);
        dbService.save(user2);

        assertThat(cacheEngine.getCurrentSize(), is(2));

        Thread.sleep(6000);

        UserDataSet loadUser1 = dbService.findById(user1.getId());
        UserDataSet loadUser2 = dbService.findById(user2.getId());

        assertThat(cacheEngine.getHits(), is(0));
        assertThat(cacheEngine.getMisses(), is(2));

        assertEquals(loadUser1, user1);
        assertEquals(loadUser2, user2);
    }
}