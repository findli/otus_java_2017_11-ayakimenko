package ru.otus.hibernate.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hibernate.dao.UserDataSetDao;
import ru.otus.hibernate.domain.UserDataSet;
import ru.otus.hibernate.util.HibernateUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

/**
 * Created by abyakimenko on 01.04.2018.
 */
public class DBServiceImpl implements DBService {

    private final SessionFactory sessionFactory;

    private static final Logger logger = LoggerFactory.getLogger(DBServiceImpl.class);

    public DBServiceImpl() {
        this.sessionFactory = HibernateUtil.createSessionFactory();
    }

    @Override
    public void save(UserDataSet user) {
        try (Session session = sessionFactory.openSession()) {
            UserDataSetDao dao = new UserDataSetDao(session);
            dao.save(user);
        }
    }

    @Override
    public void save(List<UserDataSet> users) throws SQLException {
        users.forEach(user -> {
            try (Session session = sessionFactory.openSession()) {
                UserDataSetDao dao = new UserDataSetDao(session);
                dao.save(user);
            }
        });
    }

    @Override
    public UserDataSet findById(long id) {
        return runInSession(session -> {
            UserDataSetDao dao = new UserDataSetDao(session);
            return dao.findById(id);
        });
    }

    @Override
    public UserDataSet findByName(String name) {
        return runInSession(session -> {
            UserDataSetDao dao = new UserDataSetDao(session);
            return dao.findByName(name);
        });
    }

    @Override
    public String getLocalStatus() {
        return runInSession(session -> session.getTransaction().getStatus().name());
    }

    @Override
    public List<UserDataSet> findAll() {
        return runInSession(session -> {
            UserDataSetDao dao = new UserDataSetDao(session);
            return dao.findAll();
        });
    }

    @Override
    public void close() {
//        sessionFactory.close();
        HibernateUtil.shutdown();
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }
}
