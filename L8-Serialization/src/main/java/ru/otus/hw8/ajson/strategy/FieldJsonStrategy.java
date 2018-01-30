package ru.otus.hw8.ajson.strategy;

import ru.otus.hw8.ajson.ObjectInspector;

import javax.json.*;

/**
 * Created by abyakimenko on 27.01.2018.
 */
public interface FieldJsonStrategy {
    JsonValue execute(Object field, ObjectInspector inspector);

    boolean isObjectInstance(Object object);
}
