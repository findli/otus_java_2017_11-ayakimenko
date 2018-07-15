package ru.otus.spring;

import org.kohsuke.randname.RandomNameGenerator;
import ru.otus.spring.db.CacheDbService;
import ru.otus.spring.domain.UserDataSet;

import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class UserDataSetGenerator implements Runnable {

    private final CacheDbService dbService;
    private int userCount = 0;
    private int pauseTime = 2000;

    public UserDataSetGenerator(CacheDbService dbService) {
        this.dbService = dbService;
    }

    @Override
    public void run() {
        while (true) {
            try {
                UserDataSet user = getRandomUser();
                dbService.save(user);
                userCount++;
                dbService.findById(ThreadLocalRandom.current().nextInt(0, userCount + 1));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(pauseTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private UserDataSet getRandomUser() {
        RandomNameGenerator rnd = new RandomNameGenerator();
        return new UserDataSet(rnd.next(), 25);
    }
}
