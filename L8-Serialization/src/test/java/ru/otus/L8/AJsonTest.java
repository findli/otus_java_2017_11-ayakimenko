package ru.otus.L8;

import org.junit.Before;
import org.junit.Test;
import ru.otus.L8.ajson.AJson;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by abyakimenko on 26.01.2018.
 */
public class AJsonTest {

    private AJson jsonWriter;

    @Before
    public void setUp() {
        jsonWriter = new AJson();
    }

    @Test
    public void shouldReturnPrimitiveType() {

        assertThat(jsonWriter.toJson(null), is("null"));
        assertThat(jsonWriter.toJson(10_000), is("10000"));
        assertThat(jsonWriter.toJson("10_000"), is("10_000"));
        assertThat(jsonWriter.toJson(Long.valueOf("10000123123")), is("10000123123"));
    }
}