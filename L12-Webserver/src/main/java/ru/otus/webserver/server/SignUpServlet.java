package ru.otus.webserver.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.webserver.data.Account;
import ru.otus.webserver.db.AccountDbService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignUpServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(SignUpServlet.class);

    private AccountDbService accountDBService;

    public SignUpServlet(AccountDbService accountDBService) {
        this.accountDBService = accountDBService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) {
        logger.info("###SignUpServlet doPost started");

        Account account = new Account(req.getParameter("login"), req.getParameter("password"));

        Map<String, Object> pageVariables = new HashMap<>();
        accountDBService.save(account);
        pageVariables.put("signUpStatus", "Success");

        try {
            response.getWriter().println(TemplateProcessor.instance().getPage("signup.html", pageVariables));
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            logger.error("Signup Response error", e);
        }

        logger.info("###SignUpServlet doPost started");
    }
}
