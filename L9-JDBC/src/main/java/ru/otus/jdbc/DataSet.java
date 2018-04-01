package ru.otus.jdbc;

import lombok.Data;
import ru.otus.jdbc.annotations.OrmColumn;

/**
 * Created by abyakimenko on 28.03.2018.
 */
@Data
public abstract class DataSet {
    @OrmColumn(name = "id")
    private Long id;
}
