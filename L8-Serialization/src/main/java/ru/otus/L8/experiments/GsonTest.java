package ru.otus.L8.experiments;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.L8.CustomTest;
import ru.otus.L8.Entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by abyakimenko on 26.01.2018.
 */
public class GsonTest {

    private static final Logger logger = LoggerFactory.getLogger(GsonTest.class);

    public static void main(String[] args) {

        Gson gson = new Gson();
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();

        int etra = 44;
        gson.toJson(etra);
        // PRIMITIVES
        logger.info("int primitive: {}", gson.toJson(null));
        logger.info("int primitive: {}", gson.toJson(123_000));
        logger.info("String pool: {}", gson.toJson("hello"));
        logger.info("Long wrapper: {}", gson.toJson(Long.valueOf(10)));

        Integer integer = gson.fromJson("1", int.class);
        String stringDeser = gson.fromJson("\"world\"", String.class);
        Boolean bool = gson.fromJson("true", Boolean.class);

        // ARRAY
        String stringArray = gson.toJson(new int[]{10, 100}); // [10,100]
        int[] intArray = gson.fromJson("[10,100]", int[].class);
        logger.info("string intArray: {}", stringArray);
        logger.info("int intArray: {}", intArray);

        List<Entity> listGson = Arrays.asList(new Entity(100, "name"),
                new Entity(130, "name"),
                new Entity(120, "name1"));

        String gsonList = gson.toJson(listGson);
        System.out.println("gsonList = " + gsonList);

        Entity entity = new Entity(113, "testEntity");
        entity.setRandom(1234);

        CustomTest testCustom = new CustomTest(new Date(), BigDecimal.valueOf(12.0), BigInteger.valueOf(111));
        System.out.println("testCustom = " + gson.toJson(testCustom));

        String json = gson.toJson(entity);
        Entity read = gson.fromJson(json, Entity.class);
        logger.info("Random from Entity: {}", read.getRandom());

        JSONObject obj = new JSONObject();
        obj.put("name", "mkyong.com");
        obj.put("age", new Integer(100));
        obj.toJSONString();


        JSONArray list = new JSONArray();
        list.add("msg 1");
        list.add(new Entity(130, "name"));
        list.add("msg 3");
        logger.info("JSONArray: {}", list.toJSONString());

        System.out.println("----------------------------------------------");
        List<String> listStrings = Stream.of("1", "2", "3").collect(Collectors.toList());
        String[] arrayString = {"5", "4", "3"};
        byte[] bytes = {1, 2, 3, 4, 5, 6, 7, 8};
        char[] chars = {'A', 'B', 'C', 4, 5, 6, 7, 8};


        System.out.println("listStrings = " + gson.toJson(listStrings));
        System.out.println("arrayString = " + gson.toJson(arrayString));
        System.out.println("bytes = " + gson.toJson(bytes));
        System.out.println("chars = " + gson.toJson(chars));
    }
}
