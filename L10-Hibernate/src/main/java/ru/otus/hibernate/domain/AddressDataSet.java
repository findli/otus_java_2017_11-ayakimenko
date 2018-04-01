package ru.otus.hibernate.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by abyakimenko on 01.04.2018.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "address")
public class AddressDataSet extends DataSet {

    @Column(name = "street")
    private String street;

    @Column(name = "house_number")
    private int houseNum;

    @Column(name = "flat_number")
    private int flatNumber;
}
