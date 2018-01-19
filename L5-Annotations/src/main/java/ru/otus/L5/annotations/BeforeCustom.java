package ru.otus.L5.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 * Created by abyakimenko on 19.01.2018.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={METHOD})
public @interface BeforeCustom {
}
