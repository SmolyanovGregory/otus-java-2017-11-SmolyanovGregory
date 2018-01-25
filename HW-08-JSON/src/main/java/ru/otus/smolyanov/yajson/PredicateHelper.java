package ru.otus.smolyanov.yajson;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 08 - JSON object writer
 */

class PredicateHelper {

  // Number or Boolean types sign
  static Predicate<Class> isPrimitive = c -> c.equals(byte.class) || c.equals(Byte.class)
          || c.equals(short.class) || c.equals(Short.class)
          || c.equals(int.class) || c.equals(Integer.class)
          || c.equals(long.class) || c.equals(Long.class)
          || c.equals(float.class) || c.equals(Float.class)
          || c.equals(double.class) || c.equals(Double.class)
          || c.equals(boolean.class) || c.equals(Boolean.class);

  // String or Char types sign
  static Predicate<Class> isString = c -> c.equals(String.class) || c.equals(char.class) || c.equals(Character.class);

  // Array sign
  static Predicate<Class> isArray = c -> c.isArray();

  // List sign
  static Predicate<Class> isList = c -> c.equals(List.class);

  // Set sign
  static Predicate<Class> isSet = c -> c.equals(Set.class);
}
