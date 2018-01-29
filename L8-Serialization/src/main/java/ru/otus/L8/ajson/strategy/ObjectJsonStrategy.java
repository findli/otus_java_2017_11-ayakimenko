package ru.otus.L8.ajson.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.L8.ajson.ObjectInspector;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import java.lang.reflect.Field;

/**
 * Created by abyakimenko on 27.01.2018.
 */
public class ObjectJsonStrategy implements FieldJsonStrategy {

    private static final Logger logger = LoggerFactory.getLogger(ObjectJsonStrategy.class);

    @Override
    public JsonValue execute(Object object, ObjectInspector inspector) {

        JsonObjectBuilder builder = Json.createObjectBuilder();
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            boolean accessible = field.isAccessible();
            try {
                field.setAccessible(true);
                builder.add(field.getName(), inspector.inspect(field.get(object))
                );
            } catch (Exception ex) {
                logger.error(ex.getMessage());
            } finally {
                field.setAccessible(accessible);
            }
        }

        return builder.build();
    }

    @Override
    public boolean isObjectInstance(Object object) {
        return true;
    }
}
