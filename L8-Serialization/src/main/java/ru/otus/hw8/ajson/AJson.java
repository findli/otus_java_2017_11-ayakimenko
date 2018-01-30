package ru.otus.hw8.ajson;

import javax.json.*;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by abyakimenko on 26.01.2018.
 * custom json writer
 * <p>
 * Напишите свой json object writer (object to json string) аналогичный gson на основе javax.json или
 * simple-json и Reflection.
 * <p>
 * Поддержите массивы объектов и примитивных типов, и коллекции из стандартный библиотеки.
 */
public class AJson {

    private ObjectInspector objectInspector;

    public AJson() {
        objectInspector = new ObjectInspector();
    }

    public String toJson(Object object) {

        if (object == null) {
            return "null";
        }

        if (isWrapperType(object.getClass()) || object instanceof String) {
            return object.toString();
        }

        JsonStructure treeResult = (JsonStructure) objectInspector.inspect(object);
        return writeToString(treeResult);
    }

    private String writeToString(JsonStructure input) {
        StringWriter sw = new StringWriter();
        try (JsonWriter jw = Json.createWriter(sw)) {
            jw.write(input);
        }

        return sw.toString();
    }

    private static boolean isWrapperType(Class<?> clazz) {
        return WRAPPER_TYPES.contains(clazz);
    }

    private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();

    private static Set<Class<?>> getWrapperTypes() {
        Set<Class<?>> ret = new HashSet<>();
        ret.add(Date.class);
        ret.add(String.class);
        ret.add(Boolean.class);
        ret.add(BigInteger.class);
        ret.add(BigDecimal.class);
        ret.add(Character.class);
        ret.add(Byte.class);
        ret.add(Short.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(Void.class);
        return ret;
    }
}
