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
import java.util.Objects;

public class LoginServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    private AccountDbService accountService;

    public LoginServlet(AccountDbService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("###LoginServlet doPost started");

        if (validateUser(req.getParameter("login"), req.getParameter("password"))) {
            req.getSession().setAttribute("user", "authorised");
            resp.sendRedirect("/stat");
        } else {
            resp.getWriter().println(TemplateProcessor.instance().getPage("loginFailed.html", new HashMap<>()));
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
        }
        logger.info("###LoginServlet doPost ended");
    }

    private boolean validateUser(String login, String pass) {
        Account account = accountService.findByLogin(login);
        return Objects.nonNull(account) && account.getPassword().equals(pass);
    }
}
