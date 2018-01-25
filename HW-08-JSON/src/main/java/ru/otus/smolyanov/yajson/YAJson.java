package ru.otus.smolyanov.yajson;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 08 - JSON object writer
 */

/*
Yet Another Json object writer...
*/

import java.lang.reflect.*;
import java.util.function.Function;

public class YAJson {

  public String toJson(Object object) {
    if (object == null) {
      return "null";
    }
    boolean needDelimiter = false;
    StringBuilder stringBuilder = new StringBuilder("{");

    for (Field field : object.getClass().getDeclaredFields()) {
      // only for serializible field
      if (!Modifier.isTransient(field.getModifiers())) {
        field.setAccessible(true);

        try {
          Object obj = field.get(object);

          if (obj != null) {
            if (needDelimiter) {
              stringBuilder.append(",");
            }

            // field name
            stringBuilder
                .append("\"")
                .append(field.getName())
                .append("\":");

            // try to get function for process the field value
            Function<Object, String> function = null;

            for (YAJsonType yajsonType : YAJsonType.values()) {
              if (yajsonType.getPredicate().test(field.getType())) {
                function = yajsonType.getFunction();
              }
            }

            if (function != null) {
              stringBuilder.append(function.apply(obj));
            } else {
              stringBuilder.append(toJson(obj));
            }
            needDelimiter = true;
          }

        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
    stringBuilder.append("}");

    return stringBuilder.toString();
  }
}
