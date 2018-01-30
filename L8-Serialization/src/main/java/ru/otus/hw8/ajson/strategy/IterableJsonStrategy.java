package ru.otus.hw8.ajson.strategy;

import ru.otus.hw8.ajson.ObjectInspector;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonStructure;
import java.util.Objects;

/**
 * Created by abyakimenko on 28.01.2018.
 */
public class IterableJsonStrategy implements FieldJsonStrategy {

    @Override
    public JsonStructure execute(Object object, ObjectInspector inspector) {

        JsonArrayBuilder array = Json.createArrayBuilder();
        for (Object obj : ((Iterable) object)) {
            array.add(inspector.inspect(obj));
        }

        return array.build();
    }

    @Override
    public boolean isObjectInstance(Object object) {
        return Objects.nonNull(object) && object instanceof Iterable;
    }
}
