package ru.otus.jdbc;

import javax.persistence.Entity;

/**
 * Created by abyakimenko on 28.03.2018.
 */
@Entity(name = "user_data_service")
public class UserDataSet extends DataSet {
    private String name;
    private int age;
}
