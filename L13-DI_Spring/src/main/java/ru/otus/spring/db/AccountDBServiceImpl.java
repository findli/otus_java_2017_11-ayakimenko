package ru.otus.spring.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.spring.data.Account;
import ru.otus.spring.data.AccountDao;

import java.util.function.Function;

public class AccountDBServiceImpl implements AccountDBService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountDBServiceImpl.class);

    private SessionFactory sessionFactory;
    private AccountDao accountDataSetDao;

    public AccountDBServiceImpl() {
        sessionFactory = createSessionFactory();
        accountDataSetDao = new AccountDao();
    }

    @Override
    public void save(Account account) {
        runInSession(session -> accountDataSetDao.setSession(session).save(account));
    }

    @Override
    public Account findByLogin(String login) {
        return runInSession(session -> accountDataSetDao.setSession(session).loadByLogin(login));
    }

    @Override
    public void close() {
        sessionFactory.close();
    }

    private static SessionFactory createSessionFactory() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    private <R> R runInSession(Function<Session, R> function) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        } catch (Exception e){
            LOG.error("Transaction failed", e);
            if (transaction != null) transaction.rollback();
        }
        return null;
    }
}
