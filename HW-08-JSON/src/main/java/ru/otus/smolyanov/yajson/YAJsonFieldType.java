package ru.otus.smolyanov.yajson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 08 - JSON object writer
 */

public class YAJsonFieldType {

  public enum FieldType {
    LONG,
    DOUBLE,
    BOOLEAN,
    CHAR,
    STRING,
    ARRAY,
    LIST,
    SET,
    OBJECT;
  }

  public YAJsonFieldType() {
    registerFieldTypePredicates();
  }

  private Map<Predicate<Class>, FieldType> fieldTypePredicates = new HashMap<>();

  public FieldType getFieldType(Class clazz) {
    for (Predicate<Class> predicate : fieldTypePredicates.keySet()) {
      if (predicate.test(clazz))
        return fieldTypePredicates.get(predicate);
    }
    return FieldType.OBJECT;
  }

  private void registerFieldTypePredicates() {
    fieldTypePredicates.put((c) -> c.equals(byte.class) || c.equals(Byte.class)
        || c.equals(short.class) || c.equals(Short.class)
        || c.equals(int.class) || c.equals(Integer.class)
        || c.equals(long.class) || c.equals(Long.class), FieldType.LONG);

    fieldTypePredicates.put((c) -> c.equals(float.class) || c.equals(Float.class)
        || c.equals(double.class) || c.equals(Double.class), FieldType.DOUBLE);

    fieldTypePredicates.put((c) -> c.equals(boolean.class) || c.equals(Boolean.class), FieldType.BOOLEAN);
    fieldTypePredicates.put((c) -> c.equals(String.class), FieldType.STRING);
    fieldTypePredicates.put((c) -> c.equals(char.class) || c.equals(Character.class), FieldType.CHAR);
    fieldTypePredicates.put((c) -> c.isArray(), FieldType.ARRAY);
    fieldTypePredicates.put((c) -> c.equals(List.class), FieldType.LIST);
    fieldTypePredicates.put((c) -> c.equals(Set.class), FieldType.SET);
  }
}
