package ru.otus.L8.ajson.processor;

import javax.json.JsonObjectBuilder;

/**
 * Created by abyakimenko on 27.01.2018.
 */
public interface FieldJsonStrategy {
    Object execute(Object field, JsonObjectBuilder builder);
    boolean isSameType(Object object);
}
