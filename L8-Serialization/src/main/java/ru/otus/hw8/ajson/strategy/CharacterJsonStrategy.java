package ru.otus.hw8.ajson.strategy;

import ru.otus.hw8.ajson.ObjectInspector;

import javax.json.Json;
import javax.json.JsonValue;

/**
 * Created by abyakimenko on 28.01.2018.
 */
public class CharacterJsonStrategy implements FieldJsonStrategy {

    @Override
    public JsonValue execute(Object object, ObjectInspector inspector) {
        return Json.createObjectBuilder().add("n", ((Character) object).toString()).build().getJsonString("n");
    }

    @Override
    public boolean isObjectInstance(Object object) {
        return object instanceof Character;
    }
}
