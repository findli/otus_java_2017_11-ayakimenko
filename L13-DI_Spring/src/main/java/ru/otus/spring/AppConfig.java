package ru.otus.spring;

import org.springframework.beans.factory.annotation.Qualifier;
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

    @Bean
    CacheDbService getCacheDbService(DBService dbService, CacheCore<DataSet> cacheEngine) {
        return new CacheDbService(dbService, cacheEngine);
    }

    @Bean
    UserDataSetGenerator userDataSetGenerator(@Qualifier("getCacheDbService") CacheDbService dbService) {
        return new UserDataSetGenerator(dbService);
    }

    @Bean
    CacheCore<DataSet> getCache() {
        return new CacheCoreImpl<>();
    }

    @Bean
    AccountDBService getAccountDbService() {
        return new AccountDBServiceImpl();
    }

    @Bean
    DBService getDbService() {
        return new DBServiceImpl();
    }
}
