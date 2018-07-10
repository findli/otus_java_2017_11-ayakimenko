package ru.otus.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.cache.CacheCore;
import ru.otus.spring.db.AccountDBService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("cache", appContext.getBean(CacheCore.class));
        servletContext.setAttribute("accountDBService", appContext.getBean(AccountDBService.class));

        UserDataSetGenerator dataGenerator = appContext.getBean(UserDataSetGenerator.class);
        new Thread(dataGenerator).start();
    }
}
