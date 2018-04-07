package ru.otus.hibernate.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by abyakimenko on 01.04.2018.
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = "user", callSuper = true)
@Entity
@Table(name = "phone")
public class PhoneDataSet extends DataSet {

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDataSet user;

    public PhoneDataSet(String phoneNum) {
        this.phoneNumber = phoneNum;
    }
}
