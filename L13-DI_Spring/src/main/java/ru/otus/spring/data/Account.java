package ru.otus.spring.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.hibernate.domain.DataSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account extends DataSet {
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;


    public Account(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        final Account that = (Account) o;

        return getLogin() != null ? getLogin().equals(that.getLogin()) : that.getLogin() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getLogin() != null ? getLogin().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Account{" + "login='" + login + '\'' + ", password='" + password + '\'' + '}';
    }
}
