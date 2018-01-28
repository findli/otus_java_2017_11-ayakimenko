package ru.otus.L8.ajson.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.JsonObjectBuilder;
import java.lang.reflect.Field;

/**
 * Created by abyakimenko on 27.01.2018.
 */
public class ObjectJsonStrategy implements FieldJsonStrategy {

    private static final Logger logger = LoggerFactory.getLogger(ObjectJsonStrategy.class);

    @Override
    public Object execute(Object object, JsonObjectBuilder builder) {

        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            boolean accessible = field.isAccessible();
            try {
                field.setAccessible(true);
                //builder.add(field.getName(), decomposer.walk(field.get(object)));
            } catch (Exception ex) {
                logger.error(ex.getMessage());
            } finally {
                field.setAccessible(accessible);
            }
        }

        return null;
    }

    @Override
    public boolean isSameType(Object object) {
        return true;
    }
}
