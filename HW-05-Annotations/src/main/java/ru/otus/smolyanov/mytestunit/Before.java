package ru.otus.smolyanov.mytestunit;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 05 - annotations
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Before {
}
