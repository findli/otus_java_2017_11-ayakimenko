package ru.otus.L8.ajson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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

    private static final Logger logger = LoggerFactory.getLogger(AJson.class);

    public String toJson(Object input) {

        if (input == null) {
            return "null";
        }

        if (isWrapperType(input.getClass()) || input instanceof String) {
            return input.toString();
        }

        JsonObjectBuilder builder = Json.createObjectBuilder();
        // JsonObject recursive
        navigateObjectTree(input, builder, "");

        return writeToString(builder.build());
    }

    private String writeToString(JsonObject input) {
        StringWriter sw = new StringWriter();
        try (JsonWriter jw = Json.createWriter(sw)) {
            jw.writeObject(input);
        }

        return sw.toString();
    }

    private Object navigateObjectTree(Object object, JsonObjectBuilder builder, String key) {

        boolean isWrapper = isWrapperType(object.getClass());
        boolean isPrimitive = isPrimitiveType(object.getClass());
        if (isPrimitive || isWrapper) {
            String name = object.getClass().getSimpleName().toUpperCase();
            switch (name) {

                case "STRING":
                    builder.add(key, (String) object);
                    break;
                case "INTEGER":
                case "INT":
                    builder.add(key, (int) object);
                    break;
                case "LONG":
                    builder.add(key, (long) object);
                    break;
                case "BIGINTEGER":
                    builder.add(key, (BigInteger) object);
                    break;
                case "BIGDECIMAL":
                    builder.add(key, (BigDecimal) object);
                    break;
                default:
                    builder.add(key, object.toString());
                    break;
            }
        } else if (object.getClass().isArray()) {
            for (int i = 0; i < Array.getLength(object); i++) {
                navigateObjectTree(Array.get(object, i), builder, key);
            }
        } else {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                boolean accessible = field.isAccessible();
                try {
                    field.setAccessible(true);
                    if (!Modifier.isTransient(field.getModifiers())) {

                        // builder.add(key, builder.build());
                        navigateObjectTree(field.get(object), builder, field.getName());
                    }
                } catch (Exception ex) {
                    logger.warn(ex.getMessage());
                } finally {
                    field.setAccessible(accessible);
                }
            }
        }



        /*for (Field field : fields) {
        
            if (isWrapperType(field.getType().getClass()) ||field.getType().isPrimitive() || field.getType().isInstance("")) {

                boolean accessible = field.isAccessible();
                try {
                    field.setAccessible(true);


                    switch (field.getType().getSimpleName().toUpperCase()) {

                        case "STRING":
                            builder.add(field.getName(), field.get(object).toString());
                            break;
                        case "INTEGER":
                        case "INT":
                            builder.add(field.getName(), field.getInt(object));
                            break;
                        case "LONG":
                            builder.add(field.getName(), field.getLong(object));
                            break;
                    }

                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                } finally {
                    field.setAccessible(accessible);
                }
            }

        }*/

        /*for (FieldJsonStrategy command : ProcessorFactory.getProcessors()) {
            if (command.isSameType(object)) {
                return command.execute(object, builder);
            }
        }*/

        return null;
    }


    // PRIVATE MEMBERS
    /*private static Object getFieldValue(Object object, Field field) {

        boolean isAccessible = true;
        try {
            isAccessible = field.isAccessible();
            field.setAccessible(true);

            if (field.getType().isAssignableFrom(Integer.class)) {
                return field.getInt(object);
            }

            //return field.get(object);
        } catch (IllegalAccessException e) {
            logger.error("###getFieldValue Something goes wrong ", e);
        } finally {
            if (Objects.nonNull(field) && !isAccessible) {
                field.setAccessible(false);
            }
        }
        return null;
    }*/

    private static boolean isWrapperType(Class<?> clazz) {
        return WRAPPER_TYPES.contains(clazz);
    }

    private static boolean isPrimitiveType(Class<?> clazz) {
        return PRIMITIVE_TYPES.contains(clazz);
    }

    private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();
    private static final Set<Class<?>> PRIMITIVE_TYPES = getPrimitiveTypes();

    private static Set<Class<?>> getPrimitiveTypes() {
        Set<Class<?>> ret = new HashSet<>();
        ret.add(boolean.class);
        ret.add(char.class);
        ret.add(byte.class);
        ret.add(short.class);
        ret.add(int.class);
        ret.add(long.class);
        ret.add(float.class);
        ret.add(double.class);
        ret.add(void.class);
        return ret;
    }

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
