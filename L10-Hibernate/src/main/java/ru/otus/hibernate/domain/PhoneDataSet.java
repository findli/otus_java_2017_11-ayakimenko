package ru.otus.hibernate.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by abyakimenko on 01.04.2018.
 */
@NoArgsConstructor
@Data
@Entity
@Table(name = "phone")
public class PhoneDataSet extends DataSet {

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDataSet user;
}
