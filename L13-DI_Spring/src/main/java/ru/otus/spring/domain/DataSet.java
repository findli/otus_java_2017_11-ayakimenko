package ru.otus.spring.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by abyakimenko on 01.04.2018.
 */
@Getter
@Setter
@MappedSuperclass
public class DataSet {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final DataSet dataSet = (DataSet) o;

        return getId() != null ? getId().equals(dataSet.getId()) : dataSet.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
