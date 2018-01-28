package ru.otus.L8.ajson.processor;

import javax.json.JsonObjectBuilder;

/**
 * Created by abyakimenko on 27.01.2018.
 */
public class NumericJsonStrategy implements FieldJsonStrategy {
    
    @Override
    public Object execute(Object field, JsonObjectBuilder builder) {
        return null;
    }

    @Override
    public boolean isSameType(Object object) {
        return Number.class.isAssignableFrom(object.getClass());
    }
}
