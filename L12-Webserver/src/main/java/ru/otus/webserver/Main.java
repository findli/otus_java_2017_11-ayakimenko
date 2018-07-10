package ru.otus.webserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import ru.otus.cache.CacheCore;
import ru.otus.cache.CacheCoreImpl;
import ru.otus.cache.db.CacheDbService;
import ru.otus.hibernate.domain.DataSet;
import ru.otus.hibernate.service.DBServiceImpl;
import ru.otus.webserver.db.AccountDBService;
import ru.otus.webserver.db.AccountDBServiceImpl;
import ru.otus.webserver.server.LoginServlet;
import ru.otus.webserver.server.SignUpServlet;
import ru.otus.webserver.server.StatisticServlet;

public class Main {

    private final static int PORT = 8093;
    private final static String PUBLIC_HTML = "/public_html";

    public static void main(String[] args) throws Exception {
        CacheCore<DataSet> cacheEngine = new CacheCoreImpl<>(5, 5000, 0, false);

        try (CacheDbService dbService = new CacheDbService(new DBServiceImpl(), cacheEngine)) {
            ResourceHandler resourceHandler = new ResourceHandler();
            resourceHandler.setBaseResource(Resource.newClassPathResource(PUBLIC_HTML));

            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            AccountDBService accountDBService = new AccountDBServiceImpl();

            context.addServlet(new ServletHolder(new LoginServlet(accountDBService)), "/login");
            context.addServlet(new ServletHolder(new SignUpServlet(accountDBService)), "/signup");
            context.addServlet(new ServletHolder(new StatisticServlet(cacheEngine)), "/stat");

            Server server = new Server(PORT);
            server.setHandler(new HandlerList(resourceHandler, context));

            server.start();

            UserDataSetGenerator dataGenerator = new UserDataSetGenerator(dbService);
            new Thread(dataGenerator).start();

            server.join();
        }
    }
}
