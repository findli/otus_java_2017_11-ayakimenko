package ru.otus.hibernate.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hibernate.dao.UserDataSetDao;
import ru.otus.hibernate.domain.AddressDataSet;
import ru.otus.hibernate.domain.PhoneDataSet;
import ru.otus.hibernate.domain.UserDataSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.hibernate.cfg.AvailableSettings.*;

/**
 * Created by abyakimenko on 01.04.2018.
 */
public class DBServiceImpl implements DBService {

    private final SessionFactory sessionFactory;
    private StandardServiceRegistry registry;
    private static final Logger logger = LoggerFactory.getLogger(DBServiceImpl.class);

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
        if (Objects.nonNull(registry)) {
            StandardServiceRegistryBuilder.destroy(registry);
            sessionFactory.close();
        }
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

    public SessionFactory createSessionFactory() {
        if (Objects.isNull(sessionFactory)) {
            try {

                // Create registry builder
                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Map<String, String> settings = new HashMap<>();
                settings.put(DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(URL, "jdbc:mysql://localhost:3306/jdbc?useSSL=false&serverTimezone=UTC");
                settings.put(USER, "springuser");
                settings.put(PASS, "springuser");
                settings.put(DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                settings.put(SHOW_SQL, "true");
                settings.put(HBM2DDL_AUTO, "create");
                settings.put(ENABLE_LAZY_LOAD_NO_TRANS, "true");


                registryBuilder.applySettings(settings);

                registry = registryBuilder.build();
                // add annotated classes
                MetadataSources sources = new MetadataSources(registry);
                sources.addAnnotatedClass(UserDataSet.class);
                sources.addAnnotatedClass(PhoneDataSet.class);
                sources.addAnnotatedClass(AddressDataSet.class);

                Metadata metadata = sources.getMetadataBuilder().build();
                // Create SessionFactory
                return metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                logger.error("Error creating hibernate factory", e);
                if (Objects.nonNull(registry)) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }
}
