package ru.otus.L8;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import ru.otus.L8.ajson.AJson;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by abyakimenko on 26.01.2018.
 */
public class AJsonTest {

    private AJson jsonWriter;
    private Gson gson;

    @Before
    public void setUp() {
        jsonWriter = new AJson();
        gson = new Gson();
    }

    @Test
    public void shouldSerializePrimitiveTypes() {

        assertThat(jsonWriter.toJson((byte) 22), is("22"));
        assertThat(jsonWriter.toJson(null), is("null"));
        assertThat(jsonWriter.toJson(10_000), is("10000"));
        assertThat(jsonWriter.toJson("10_000"), is("10_000"));
        assertThat(jsonWriter.toJson(Long.valueOf("10000123123")), is("10000123123"));
    }

    @Test
    public void shouldSerializeObject() {

        SimpleEntity simpleEntity = new SimpleEntity(11, 12, 55, 555);

        String result = jsonWriter.toJson(simpleEntity);


        Entity test1 = new Entity(113, "testEntity");
        test1.setRandom(1234);

        assertThat(gson.toJson(test1), is("{\"id\":113,\"name\":\"testEntity\",\"count\":45}"));
        assertThat(jsonWriter.toJson(test1), is("{\"id\":113,\"name\":\"testEntity\",\"count\":45}"));
        assertThat(jsonWriter.toJson(test1), is("{\"id\":113,\"name\":\"testEntity\",\"count\":45}"));

        CustomTest testCustom = new CustomTest(new Date(), BigDecimal.valueOf(12.0), BigInteger.valueOf(111));
        assertThat(jsonWriter.toJson(testCustom), is("{\"decimal\":12.0,\"integer\":111,\"embeddedEntity\":{\"id\":111,\"name\":\"embeddedEntity\",\"count\":45}}"));
    }

    @Test
    public void shouldSerializeArraysAndCollections() {

        List<String> listStrings = Stream.of("1", "2", "3").collect(Collectors.toList());
        assertThat(jsonWriter.toJson(listStrings), is("[\"1\",\"2\",\"3\"]"));
        String[] arrayString = {"5", "4", "3"};
        assertThat(jsonWriter.toJson(arrayString), is("[\"5\",\"4\",\"3\"]"));
        byte[] bytes = {1, 2, 3, 4, 5, 6, 7, 8};
        assertThat(jsonWriter.toJson(bytes), is("[1,2,3,4,5,6,7,8]"));
        char[] chars = {'A', 'B', 'C', 4, 5, 6, 7, 8};
        assertThat(jsonWriter.toJson(chars), is("[\"A\",\"B\",\"C\",\"\\u0004\",\"\\u0005\",\"\\u0006\",\"\\u0007\",\"\\b\"]"));


        List<Entity> listObjects = Arrays.asList(new Entity(100, "name"),
                new Entity(130, "name"),
                new Entity(120, "name1"));

        assertThat(jsonWriter.toJson(listObjects), is("[{\"id\":100,\"name\":\"name\",\"count\":45},{\"id\":130,\"name\":\"name\",\"count\":45},{\"id\":120,\"name\":\"name1\",\"count\":45}]"));
    }
}