package ru.otus.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.cache.CacheCore;
import ru.otus.cache.CacheCoreImpl;
import ru.otus.cache.db.CacheDbService;
import ru.otus.hibernate.domain.DataSet;
import ru.otus.hibernate.service.DBService;
import ru.otus.hibernate.service.DBServiceImpl;
import ru.otus.spring.db.AccountDBService;
import ru.otus.spring.db.AccountDBServiceImpl;

@Configuration
@ComponentScan
public class AppConfig {

    @Bean(name = "userGenerator")
    UserDataSetGenerator userDataSetGenerator(CacheDbService dbService) {
        return new UserDataSetGenerator(dbService);
    }

    @Bean(name = "cache")
    CacheCore<DataSet> getCache() {
        return new CacheCoreImpl<>();
    }

    @Bean(name = "cacheDao")
    CacheDbService getCacheDbService(DBService dbService, CacheCore<DataSet> cacheEngine) {
        return new CacheDbService(dbService, cacheEngine);
    }

    @Bean(name = "accountDao")
    AccountDBService getAccountDbService() {
        return new AccountDBServiceImpl();
    }

    @Bean(name = "dbService")
    DBService getDbService() {
        return new DBServiceImpl();
    }
}
