package ru.otus.gc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abyakimenko on 31.05.2018.
 */
public class AppLeaked {
    private static final Logger logger = LoggerFactory.getLogger(AppLeaked.class);

    private List<String> list = new ArrayList<>();
    private final int increase_size = 200_000;
    private final int decrease_size = 5_000;

    private AppLeaked() {
    }

    public static AppLeaked buildInstance() {
        return new AppLeaked();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void startApp() {
        while (true) {
            doWork();
            doLeak();
        }
    }

    private void doWork() {
        for (int i = 0; i < increase_size; i++) {
            list.add(String.valueOf(i));
        }
        logger.info("Added {} objects",increase_size);
    }

    private void doLeak() {
        for (int i = 0; i < decrease_size; i++) {
            list.remove(String.valueOf(i));
        }
        logger.info("Removed {} objects",decrease_size);                                                                                                                     
    }
}
