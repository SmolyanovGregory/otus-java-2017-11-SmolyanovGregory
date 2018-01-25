package ru.otus.smolyanov.yajson;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 08 - JSON object writer
 */

import java.lang.reflect.*;
import java.util.function.Function;
import java.util.List;
import java.util.Set;

class FunctionHelper {
  private static String NULL = "null";

  static Function<Object, String> processPrimitive = o -> o != null ? o.toString() : NULL;

  static Function<Object, String> processString = o -> o != null ? "\""+o.toString()+"\"" : NULL;

  static Function<Object, String> processArray = o -> {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("[");

      for (int i = 0; i < Array.getLength(o); i++) {
        Object element = Array.get(o, i);

        if (i > 0) {
          stringBuilder.append(",");
        }

        if (element != null) {
          Function<Object, String> function = getFunctionForProcess(element.getClass());
          stringBuilder.append(function != null ? function.apply(element) : FunctionHelper.processObject(element));
        } else {
          stringBuilder.append(NULL);
        }
      }

      stringBuilder.append("]");
      return stringBuilder.toString();
  };

  static Function<Object, String> processList = o -> processArray.apply(((List)o).toArray());

  static Function<Object, String> processSet = o -> processArray.apply(((Set)o).toArray());

  static String processObject(Object object) {
    if (object == null) {
      return NULL;
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
            Function<Object, String> function = getFunctionForProcess(field.getType());
            stringBuilder.append(function != null ? function.apply(obj) : FunctionHelper.processObject(obj));

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

  public static Function<Object, String> getFunctionForProcess(Class klass) {
    Function<Object, String> result = null;

    for (YAJsonType yajsonType : YAJsonType.values()) {
      if (yajsonType.getPredicate().test(klass)) {
        result = yajsonType.getFunction();
      }
    }

    return result;
  }
}
