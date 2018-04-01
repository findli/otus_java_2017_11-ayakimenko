package ru.otus.jdbc;

import lombok.Data;
import ru.otus.jdbc.annotations.OrmColumn;

/**
 * Created by abyakimenko on 28.03.2018.
 */
@Data
public class UserDataSet extends DataSet {
    @OrmColumn(name = "age")
    private long age;
    @OrmColumn(name = "name")
    private String name;

    public UserDataSet(long age, String name) {
        this.age = age;
        this.name = name;
    }

    public UserDataSet(long id, String name, long age) {
        super.setId(id);
        this.age = age;
        this.name = name;
    }
}
