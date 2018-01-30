package ru.otus.hw8.ajson.strategy;

import ru.otus.hw8.ajson.ObjectInspector;

import javax.json.Json;
import javax.json.JsonValue;

/**
 * Created by abyakimenko on 27.01.2018.
 */
public class StringJsonStrategy implements FieldJsonStrategy {

    @Override
    public JsonValue execute(Object object, ObjectInspector inspector) {
        return Json.createObjectBuilder().add("n", (String) object).build().getJsonString("n");
    }

    @Override
    public boolean isObjectInstance(Object object) {
        return object instanceof String;
    }
}
