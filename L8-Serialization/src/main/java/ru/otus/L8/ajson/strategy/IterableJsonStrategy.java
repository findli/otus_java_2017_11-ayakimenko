package ru.otus.L8.ajson.strategy;

/**
 * Created by abyakimenko on 28.01.2018.
 */
//public class IterableJsonStrategy implements FieldJsonStrategy {
//
//    @Override
//    public JsonValue execute(Object object, ObjectInspector inspector) {
//
//        JsonArrayBuilder array = Json.createArrayBuilder();
//        for (Object obj : ((Iterable) object)) {
//            array.add(inspector.inspect(obj));
//        }
//
//        return array.build();
//    }
//
//    @Override
//    public boolean isObjectInstance(Object object) {
//        return Objects.isNull(object);
//    }
//}
