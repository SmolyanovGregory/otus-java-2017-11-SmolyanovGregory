package ru.otus.smolyanov.yajson;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 08 - JSON object writer
 */

/*
Yet Another Json object writer...
*/

import java.util.function.Function;

public class YAJson {

  public String toJson(Object object) {
    if (object == null) {
      return "null";
    }

    StringBuilder stringBuilder = new StringBuilder();

    // try to get function for process the field value
    Function<Object, String> function = null;

    for (YAJsonType yajsonType : YAJsonType.values()) {
      if (yajsonType.getPredicate().test(object.getClass())) {
        function = yajsonType.getFunction();
      }
    }

    if (function != null) {
      stringBuilder.append(function.apply(object));
    } else {
      stringBuilder.append(FunctionHelper.processObject(object));
    }

    return stringBuilder.toString();
  }
}
