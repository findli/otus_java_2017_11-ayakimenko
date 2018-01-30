package ru.otus.hw8.ajson.strategy;

import ru.otus.hw8.ajson.ObjectInspector;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonStructure;
import java.lang.reflect.Array;
import java.util.Objects;

/**
 * Created by abyakimenko on 28.01.2018.
 */
public class ArrayJsonStrategy implements FieldJsonStrategy {

    @Override
    public JsonStructure execute(Object object, ObjectInspector inspector) {

        JsonArrayBuilder array = Json.createArrayBuilder();
        for (int i = 0; i < Array.getLength(object); i++) {
            array.add(inspector.inspect(Array.get(object, i)));
        }
        return array.build();
    }

    @Override
    public boolean isObjectInstance(Object object) {
        return Objects.nonNull(object) && object.getClass().isArray();
    }
}
