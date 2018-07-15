package ru.otus.spring.server;

import org.eclipse.jetty.http.HttpStatus;
import ru.otus.spring.cache.CacheCore;
import ru.otus.spring.domain.DataSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StatisticServlet extends BaseServlet {

    private CacheCore<DataSet> cache;

    @Override
    @SuppressWarnings("unchecked")
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.cache = (CacheCore<DataSet>) getContext().getBean("cache");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Object userAttr = req.getSession().getAttribute("user");
        if (Objects.nonNull(userAttr) && userAttr.equals("authorised")) {
            resp.setStatus(HttpStatus.OK_200);
            Map<String, Object> pageVariables = createPageVariablesMap();

            resp.getWriter().println(TemplateProcessor.instance().getPage("stat.html", pageVariables));
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private Map<String, Object> createPageVariablesMap() {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("cacheSize", cache.getCurrentSize());
        pageVariables.put("cacheHit", cache.getHits());
        pageVariables.put("cacheMiss", cache.getMisses());
        return pageVariables;
    }
}