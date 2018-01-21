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

  private enum FieldType {
    LONG,
    DOUBLE,
    BOOLEAN,
    CHAR,
    STRING,
    ARRAY,
    LIST,
    SET,
    OBJECT
  }

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
      if (!Modifier.isTransient(field.getModifiers()) && !field.getName().equals("this$0")) {
        field.setAccessible(true);

        try {
          Object fieldValue = field.get(object);

          if (fieldValue != null) {
            Object[] objArray = null;

            switch (getFieldType(fieldValue.getClass())) {
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

        switch (getFieldType(element.getClass())) {
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

  private FieldType getFieldType(Class valueClass) {
    Set<Class> interfaces = new HashSet<>();
    for(Class clazz : valueClass.getInterfaces()) {
      interfaces.add(clazz);
    }

    if (valueClass.equals(byte.class)
        || valueClass.equals(Byte.class)
        || valueClass.equals(short.class)
        || valueClass.equals(Short.class)
        || valueClass.equals(int.class)
        || valueClass.equals(Integer.class)
        || valueClass.equals(long.class)
        || valueClass.equals(Long.class)
        )
      return FieldType.LONG;
    else if (valueClass.equals(float.class)
        || valueClass.equals(Float.class)
        || valueClass.equals(double.class)
        || valueClass.equals(Double.class)
        )
      return FieldType.DOUBLE;
    else if (valueClass.equals(boolean.class)
        || valueClass.equals(Boolean.class)
        )
      return FieldType.BOOLEAN;
    else if (valueClass.equals(char.class)
        || valueClass.equals(Character.class)
        )
      return FieldType.CHAR;
    else if (valueClass.equals(String.class))
      return FieldType.STRING;
    else if (valueClass.isArray())
      return FieldType.ARRAY;
    else if (interfaces.contains(List.class))
      return FieldType.LIST;
    else if (interfaces.contains(Set.class))
      return FieldType.SET;

    return FieldType.OBJECT;
  }
}
