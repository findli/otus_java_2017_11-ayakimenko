package ru.otus.hw8.ajson.strategy;

import ru.otus.hw8.ajson.ObjectInspector;

import javax.json.Json;
import javax.json.JsonValue;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by abyakimenko on 27.01.2018.
 */
public class NumericJsonStrategy implements FieldJsonStrategy {

    @Override
    public JsonValue execute(Object object, ObjectInspector inspector) {
        
        if (object instanceof BigInteger) {
            return Json.createObjectBuilder().add("n", (BigInteger) object).build().getJsonNumber("n");
        }
        if (object instanceof Integer) {
            return Json.createObjectBuilder().add("n", (int) object).build().getJsonNumber("n");
        }
        if (object instanceof Long) {
            return Json.createObjectBuilder().add("n", (long) object).build().getJsonNumber("n");
        }
        if (object instanceof Double) {
            return Json.createObjectBuilder().add("n", (double) object).build().getJsonNumber("n");
        }
        if (object instanceof Float) {
            return Json.createObjectBuilder().add("n", (float) object).build().getJsonNumber("n");
        }
        if (object instanceof BigDecimal) {
            return Json.createObjectBuilder().add("n", (BigDecimal) object).build().getJsonNumber("n");
        }
        if (object instanceof Byte) {
            return Json.createObjectBuilder().add("n", (byte) object).build().getJsonNumber("n");
        }

        return Json.createObjectBuilder().add("n", (double) object).build().getJsonString("unknown number");
    }

    @Override
    public boolean isObjectInstance(Object object) {
        return Number.class.isAssignableFrom(object.getClass());
    }
}
