package ru.otus.L8.ajson;

import org.json.simple.JSONValue;

/**
 * Created by abyakimenko on 26.01.2018.
 * custom json writer
 * <p>
 * Напишите свой json object writer (object to json string) аналогичный gson на основе javax.json или
 * simple-json и Reflection.
 * <p>
 * Поддержите массивы объектов и примитивных типов, и коллекции из стандартный библиотеки.
 */
public class AJson {

    public String toJson(Object input) {

        if (input == null) {
            return "null";
        }

        // if type is primitive - return its toString()

        return JSONValue.toJSONString(input);
    }
}
