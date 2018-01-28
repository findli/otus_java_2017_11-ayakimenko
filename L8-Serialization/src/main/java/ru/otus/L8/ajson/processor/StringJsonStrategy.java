package ru.otus.L8.ajson.processor;

import javax.json.JsonObjectBuilder;

/**
 * Created by abyakimenko on 27.01.2018.
 */
public class StringJsonStrategy implements FieldJsonStrategy {
    @Override
    public Object execute(Object field, JsonObjectBuilder builder) {
        return field;
    }

    @Override
    public boolean isSameType(Object object) {
        return object instanceof String;
    }
}
