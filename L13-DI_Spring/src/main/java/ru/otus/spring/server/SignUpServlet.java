package ru.otus.spring.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.spring.data.Account;
import ru.otus.spring.db.AccountDBService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SignUpServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(SignUpServlet.class);

    private AccountDBService accountDBService;

    public SignUpServlet(AccountDBService accountDBService) {
        this.accountDBService = accountDBService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Account account = new Account(req.getParameter("login"), req.getParameter("password"));

        Map<String, Object> pageVariables = new HashMap<>();
        try {
            accountDBService.save(account);
            pageVariables.put("signUpStatus", "Success");
        } catch (SQLException e) {
            logger.error("Unable to sign up", e);
            pageVariables.put("signUpStatus", "Failed");
        }

        resp.getWriter().println(TemplateProcessor.instance().getPage("signup.html", pageVariables));
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}