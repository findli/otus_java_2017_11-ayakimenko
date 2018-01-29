package ru.otus.L8.ajson.strategy;

import ru.otus.L8.ajson.ObjectInspector;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

/**
 * Created by abyakimenko on 27.01.2018.
 */
public interface FieldJsonStrategy {
    JsonValue execute(Object field, ObjectInspector inspector);

    boolean isObjectInstance(Object object);

    default JsonObject enrich(JsonObject source, String key, String value) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add(key, value);
        source.forEach(builder::add);
        return builder.build();
    }
}
