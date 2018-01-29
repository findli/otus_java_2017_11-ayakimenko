package ru.otus.L8.ajson;

import ru.otus.L8.ajson.strategy.FieldJsonStrategy;
import ru.otus.L8.ajson.wrapper.ProcessorFactory;

import javax.json.JsonValue;

/**
 * Created by abyakimenko on 28.01.2018.
 */
public class ObjectInspector {

    public JsonValue inspect(Object object) {

        for (FieldJsonStrategy strategy : ProcessorFactory.getProcessors()) {
            if (strategy.isObjectInstance(object)) {
                return strategy.execute(object, this);
            }
        }

        return null;
    }
}
