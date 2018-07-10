package ru.otus.spring.data;

import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;

public class AccountDao {

    private Session session;

    public AccountDao setSession(Session session) {
        this.session = session;
        return this;
    }

    public Account loadByLogin(String login) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Account> criteria = builder.createQuery(Account.class);
        criteria.where(builder.equal(criteria.from(Account.class).get("login"), login));
        return session.createQuery(criteria)
            .uniqueResult();
    }

    public Serializable save(Account account) {
        return session.save(account);
    }
}
