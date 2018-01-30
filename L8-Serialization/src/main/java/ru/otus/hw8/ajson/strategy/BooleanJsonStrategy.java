package ru.otus.hw8.ajson.strategy;

import ru.otus.hw8.ajson.ObjectInspector;

import javax.json.JsonValue;

/**
 * Created by abyakimenko on 28.01.2018.
 */
public class BooleanJsonStrategy implements FieldJsonStrategy {

    @Override
    public JsonValue execute(Object object, ObjectInspector inspector) {
        return (Boolean) object ? JsonValue.TRUE : JsonValue.FALSE;
    }

    @Override
    public boolean isObjectInstance(Object object) {
        return object instanceof Boolean;
    }
}
