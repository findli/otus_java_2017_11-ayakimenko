package ru.otus.hibernate.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hibernate.domain.AddressDataSet;
import ru.otus.hibernate.domain.PhoneDataSet;
import ru.otus.hibernate.domain.UserDataSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.hibernate.cfg.AvailableSettings.*;

public class HibernateUtil {

    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory createSessionFactory() {
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
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                logger.error("Error creating hibernate factory", e);
                if (Objects.nonNull(registry)) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (Objects.nonNull(registry)) {
            StandardServiceRegistryBuilder.destroy(registry);
            sessionFactory.close();
            sessionFactory = null;
        }
    }
}