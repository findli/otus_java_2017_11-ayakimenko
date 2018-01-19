package ru.otus.L5.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.L5.annotations.AfterCustom;
import ru.otus.L5.annotations.BeforeCustom;
import ru.otus.L5.annotations.TestCustom;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by abyakimenko on 19.01.2018.
 */
public class CustomUnitTest {

    private static final Logger logger = LoggerFactory.getLogger(CustomUnitTest.class);

    @BeforeCustom
    public void setUp() {
        logger.info("Set up something {}", CustomUnitTest.class.getName());
    }

    @TestCustom
    public void shouldPassInteger() {
        assertThat(1, is(1));
    }

    @TestCustom
    public void shouldPassBool() {
        assertThat(true, is(true));
    }

    @TestCustom
    public void shouldFail() {
        assertThat(1, is(5));
    }

    @AfterCustom
    public void afterAll() {
        logger.info("Method after all tests {}", CustomUnitTest.class.getName());
    }
}
