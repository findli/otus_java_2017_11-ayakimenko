package ru.otus.spring.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.spring.domain.UserDataSet;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by abyakimenko on 01.04.2018.
 */
public class DBServiceImpl implements DBService {

    private static final Logger logger = LoggerFactory.getLogger(DBServiceImpl.class);

    private final SessionFactory sessionFactory;

    public DBServiceImpl() {
        this.sessionFactory = createSessionFactory();
    }

    @Override
    public void save(UserDataSet user) {
        runInSessionModify(session -> {
            UserDataSetDao dao = new UserDataSetDao(session);
            dao.save(user);
        });
    }

    @Override
    public void save(List<UserDataSet> users) {
        users.forEach(this::save);
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
        sessionFactory.close();
    }

    private void runInSessionModify(Consumer<Session> consumer) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            consumer.accept(session);
            transaction.commit();
        }
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }

    private static SessionFactory createSessionFactory() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }
}
