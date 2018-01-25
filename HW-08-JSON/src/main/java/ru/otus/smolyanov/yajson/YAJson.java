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

    Function<Object, String> function = FunctionHelper.getFunctionForProcess(object.getClass());
    stringBuilder.append(function != null ? function.apply(object) : FunctionHelper.processObject(object));

    return stringBuilder.toString();
  }
}
