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

public class YAJson {

  private final YAJsonFieldType yaJsonFieldType = new YAJsonFieldType();

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
          Object fieldValue = field.get(object);

          if (fieldValue != null) {
            Object[] objArray = null;

            switch (yaJsonFieldType.getFieldType(field.getType())) {
              case LONG:
                builder.add(field.getName(), Long.valueOf(fieldValue.toString()));
                break;
              case DOUBLE:
                builder.add(field.getName(), Double.valueOf(fieldValue.toString()));
                break;
              case BOOLEAN:
                builder.add(field.getName(), Boolean.valueOf(fieldValue.toString()));
                break;
              case CHAR:
                builder.add(field.getName(), fieldValue.toString());
                break;
              case STRING:
                builder.add(field.getName(), fieldValue.toString());
                break;
              case ARRAY:
                builder.add(field.getName(), getJsonArrayBuilder(fieldValue));
                break;
              case LIST:
                objArray = ((List) fieldValue).toArray();
                builder.add(field.getName(), getJsonArrayBuilder(objArray));
                break;
              case SET:
                objArray = ((Set) fieldValue).toArray();
                builder.add(field.getName(), getJsonArrayBuilder(objArray));
                break;
              case OBJECT:
                builder.add(field.getName(), getJsonObjectBuilder(fieldValue));
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
        Object[] objArray = null;

        switch (yaJsonFieldType.getFieldType(element.getClass())) {
          case LONG:
            builder.add(Long.valueOf(element.toString()));
            break;
          case DOUBLE:
            builder.add(Double.valueOf(element.toString()));
            break;
          case BOOLEAN:
            builder.add(Boolean.valueOf(element.toString()));
            break;
          case CHAR:
            builder.add(element.toString());
            break;
          case STRING:
            builder.add(element.toString());
            break;
          case ARRAY:
            builder.add(getJsonArrayBuilder(element));
            break;
          case LIST:
            objArray = ((List) element).toArray();
            builder.add(getJsonArrayBuilder(objArray));
            break;
          case SET:
            objArray = ((Set) element).toArray();
            builder.add(getJsonArrayBuilder(objArray));
            break;
          case OBJECT:
            builder.add(getJsonObjectBuilder(element));
            break;
        }
      } else {
        builder.addNull();
      }
    }

    return builder;
  }

}
