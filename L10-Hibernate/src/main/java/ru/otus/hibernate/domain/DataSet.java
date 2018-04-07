package ru.otus.hibernate.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by abyakimenko on 01.04.2018.
 */
@Data
@MappedSuperclass
class DataSet {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
