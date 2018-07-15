package ru.otus.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.cache.CacheCore;
import ru.otus.spring.cache.CacheCoreImpl;
import ru.otus.spring.db.*;
import ru.otus.spring.domain.DataSet;

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
