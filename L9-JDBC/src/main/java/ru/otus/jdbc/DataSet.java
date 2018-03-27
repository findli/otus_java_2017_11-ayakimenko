package ru.otus.jdbc;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by abyakimenko on 28.03.2018.
 */
@Data
@Entity
public abstract class DataSet {
    @Id
    private Long id;
}
