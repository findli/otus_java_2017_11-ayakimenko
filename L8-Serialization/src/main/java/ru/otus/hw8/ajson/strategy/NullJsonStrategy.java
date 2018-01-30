package ru.otus.hw8.ajson.strategy;

import ru.otus.hw8.ajson.ObjectInspector;

import javax.json.JsonValue;
import java.util.Objects;

/**
 * Created by abyakimenko on 28.01.2018.
 */
public class NullJsonStrategy implements FieldJsonStrategy {
    @Override
    public JsonValue execute(Object field, ObjectInspector inspector) {
        return null;
    }

    @Override
    public boolean isObjectInstance(Object object) {
        return Objects.isNull(object);
    }
}
