package ru.otus.spring.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.Objects;

public class BaseServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(BaseServlet.class);

    private ApplicationContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        if (Objects.isNull(context)) {
            context = (ApplicationContext) config.getServletContext().getAttribute("applicationCtx");
        }
    }

    protected ApplicationContext getContext() {
        return context;
    }
}
