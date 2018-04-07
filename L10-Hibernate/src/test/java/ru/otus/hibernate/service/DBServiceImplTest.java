package ru.otus.hibernate.service;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.otus.hibernate.domain.AddressDataSet;
import ru.otus.hibernate.domain.PhoneDataSet;
import ru.otus.hibernate.domain.UserDataSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by abyakimenko on 07.04.2018.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DBServiceImplTest {

    private DBService dbService;

    @Before
    public void setUp() {
        dbService = new DBServiceImpl();
    }

    @After
    public void tearDown() throws Exception {
        dbService.close();
    }

    @Test
    public void shouldSaveUser() throws SQLException {
        UserDataSet user1 = new UserDataSet("user1", 30);
        UserDataSet user2 = new UserDataSet("user2", 33);

        dbService.save(user1);
        dbService.save(user2);

        assertThat(dbService.findById(1).toString(), is(user1.toString()));
        assertThat(dbService.findById(2).toString(), is(user2.toString()));
    }

    @Test
    public void shouldAddUserWithAddress() throws SQLException {
        UserDataSet user1 = new UserDataSet("user1", 30);
        AddressDataSet address = new AddressDataSet("StreetNameTest", 33, 22);
        user1.setAddress(address);

        dbService.save(user1);
        assertThat(dbService.findById(1).toString(), is(user1.toString()));
    }

    @Test
    public void shouldAddUserWithPhone() throws SQLException {
        UserDataSet user1 = new UserDataSet("user1", 30);
        PhoneDataSet phone1 = new PhoneDataSet("12345-67890");
        PhoneDataSet phone2 = new PhoneDataSet("11111-22222");
        user1.addPhone(phone1);
        user1.addPhone(phone2);

        dbService.save(user1);

        UserDataSet user = dbService.findById(1);
        assertThat(user.getPhones(), hasSize(2));
    }

    @Test
    public void getLocalStatus() {
        assertEquals("ACTIVE", dbService.getLocalStatus());
    }

    @Test
    public void findByName() throws SQLException {
        UserDataSet user1 = new UserDataSet("user1", 30);
        dbService.save(user1);
        assertEquals("user1", dbService.findByName("user1").getName());
    }

    @Test
        public void shouldFindAll() throws SQLException {

        List<UserDataSet> list = new ArrayList<>();
        list.add(new UserDataSet("name1", 30));
        list.add(new UserDataSet("name2", 31));
        list.add(new UserDataSet("name3", 32));

        dbService.save(list);

        List<UserDataSet> userDataSet = dbService.findAll();
        assertThat(userDataSet, hasSize(3));
    }
}