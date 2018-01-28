package ru.otus.L8.ajson.wrapper;

import ru.otus.L8.ajson.processor.FieldJsonStrategy;
import ru.otus.L8.ajson.processor.NumericJsonStrategy;
import ru.otus.L8.ajson.processor.ObjectJsonStrategy;
import ru.otus.L8.ajson.processor.StringJsonStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abyakimenko on 27.01.2018.
 */
public class ProcessorFactory {

    private static final List<FieldJsonStrategy> processors = new ArrayList<>();

    static {
        processors.add(new NumericJsonStrategy());
        processors.add(new StringJsonStrategy());
        processors.add(new ObjectJsonStrategy());
    }

    public static List<FieldJsonStrategy> getProcessors() {
        return processors;
    }
}
