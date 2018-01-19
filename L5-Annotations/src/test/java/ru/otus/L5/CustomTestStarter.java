package ru.otus.L5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.L5.core.CustomUnitTestCore;
import ru.otus.L5.tests.CustomUnitTest;

/**
 * Created by abyakimenko on 19.01.2018.
 */
public class CustomTestStarter {

    private static final Logger logger = LoggerFactory.getLogger(CustomTestStarter.class);

    public static void main(String[] args) {

        logger.info("Running tests for testing annotations...");

        CustomUnitTestCore.runClassMethods(CustomUnitTest.class);

        logger.info("Running tests by package name...");
        
        CustomUnitTestCore.runPackageClasses("ru.otus.L5.tests");

        CustomUnitTestCore.runPackageClasses("ru.otus.L5");
    }
}
