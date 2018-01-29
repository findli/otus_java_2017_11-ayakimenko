package ru.otus.L8.ajson.strategy;

import ru.otus.L8.ajson.ObjectInspector;

import javax.json.Json;
import javax.json.JsonValue;

/**
 * Created by abyakimenko on 27.01.2018.
 */
public class NumericJsonStrategy implements FieldJsonStrategy {

    @Override
    public JsonValue execute(Object object, ObjectInspector inspector) {

        //object.toString();
        return Json.createObjectBuilder().addNull(object.toString()).build();
    }

    @Override
    public boolean isObjectInstance(Object object) {
        return Number.class.isAssignableFrom(object.getClass());
    }
}
