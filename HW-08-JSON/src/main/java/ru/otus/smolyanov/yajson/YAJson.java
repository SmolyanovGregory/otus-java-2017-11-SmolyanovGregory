package ru.otus.smolyanov.yajson;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 08 - JSON object writer
 */

/*
Yet Another Json object writer...
*/

import java.io.StringWriter;
import java.lang.reflect.*;
import java.util.*;
import javax.json.*;
import java.util.function.Function;

public class YAJson {

  private final YAJsonFieldType yaJsonFieldType = new YAJsonFieldType();

  private Function<Object, Long> longFunction = o -> Long.valueOf(o.toString());
  private Function<Object, Double> doubleFunction = o -> Double.valueOf(o.toString());
  private Function<Object, Boolean> booleanFunction = o -> Boolean.valueOf(o.toString());
  private Function<Object, String> stringFunction = o -> o.toString();
  private Function<Object, JsonArrayBuilder> jsonArrayBuilderFunction = o -> getJsonArrayBuilder(o);
  private Function<Object, JsonObjectBuilder> jsonObjectBuilderFunction = o -> getJsonObjectBuilder(o);

  public YAJson() {
  }

  public String toJsonString(Object object) {
    if (object == null) {
      return "null";
    }

    JsonObjectBuilder builder = getJsonObjectBuilder(object);

    javax.json.JsonObject jsonObject = builder.build();
    StringWriter stringWriter = new StringWriter();

    try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
      jsonWriter.writeObject(jsonObject);
    }
    return stringWriter.toString();
  }

  private JsonObjectBuilder getJsonObjectBuilder(Object object) {
    JsonObjectBuilder builder = Json.createObjectBuilder();
    for (Field field : object.getClass().getDeclaredFields()) {
      // only for serializible field
      if (!Modifier.isTransient(field.getModifiers())) {
        field.setAccessible(true);

        try {
          Object obj = field.get(object);

          if (obj != null) {
            switch (yaJsonFieldType.getFieldType(field.getType())) {
              case LONG:
                builder.add(field.getName(), longFunction.apply(obj));
                break;
              case DOUBLE:
                builder.add(field.getName(), doubleFunction.apply(obj));
                break;
              case BOOLEAN:
                builder.add(field.getName(), booleanFunction.apply(obj));
                break;
              case CHAR:
                builder.add(field.getName(), stringFunction.apply(obj));
                break;
              case STRING:
                builder.add(field.getName(), stringFunction.apply(obj));
                break;
              case ARRAY:
                builder.add(field.getName(), jsonArrayBuilderFunction.apply(obj));
                break;
              case LIST:
                builder.add(field.getName(), jsonArrayBuilderFunction.apply(((List) obj).toArray()));
                break;
              case SET:
                builder.add(field.getName(), jsonArrayBuilderFunction.apply(((Set) obj).toArray()));
                break;
              case OBJECT:
                builder.add(field.getName(), jsonObjectBuilderFunction.apply(obj));
                break;
            }
          }
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
    return builder;
  }

  private JsonArrayBuilder getJsonArrayBuilder(Object array) {
    JsonArrayBuilder builder = Json.createArrayBuilder();

    for (int i = 0; i < Array.getLength(array); i++) {
      Object element = Array.get(array, i);

      if (element != null) {
        switch (yaJsonFieldType.getFieldType(element.getClass())) {
          case LONG:
            builder.add(longFunction.apply(element));
            break;
          case DOUBLE:
            builder.add(doubleFunction.apply(element));
            break;
          case BOOLEAN:
            builder.add(booleanFunction.apply(element));
            break;
          case CHAR:
            builder.add(stringFunction.apply(element));
            break;
          case STRING:
            builder.add(stringFunction.apply(element));
            break;
          case ARRAY:
            builder.add(jsonArrayBuilderFunction.apply(element));
            break;
          case LIST:
            builder.add(jsonArrayBuilderFunction.apply(((List) element).toArray()));
            break;
          case SET:
            builder.add(jsonArrayBuilderFunction.apply(((Set) element).toArray()));
            break;
          case OBJECT:
            builder.add(jsonObjectBuilderFunction.apply(element));
            break;
        }
      } else {
        builder.addNull();
      }
    }

    return builder;
  }


}
