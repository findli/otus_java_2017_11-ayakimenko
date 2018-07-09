package ru.otus.hibernate.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "address")
public class AddressDataSet extends DataSet {
    @Column(name = "street")
    private String street;
    @Column(name = "house_number")
    private int houseNum;
    @Column(name = "flat_number")
    private int flatNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        final AddressDataSet that = (AddressDataSet) o;

        if (getHouseNum() != that.getHouseNum()) return false;
        if (getFlatNumber() != that.getFlatNumber()) return false;
        return getStreet() != null ? getStreet().equals(that.getStreet()) : that.getStreet() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getStreet() != null ? getStreet().hashCode() : 0);
        result = 31 * result + getHouseNum();
        result = 31 * result + getFlatNumber();
        return result;
    }
}
