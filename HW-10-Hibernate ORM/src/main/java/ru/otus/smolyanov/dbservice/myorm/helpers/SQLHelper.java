package ru.otus.smolyanov.dbservice.myorm.helpers;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 10 - Hibernate ORM
 */

import java.lang.reflect.Field;
import java.util.function.Predicate;

public class SQLHelper {
  public static final String EXISTING_TABLES_LIST_SQL = "select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_TYPE = 'TABLE'";
  public static final String SELECT_ALL_SQL = "select * from %s order by id";
  public static final String SELECT_SINGLE_ROW_SQL = "select * from %s where id=%d";

  public static String getCreateTableStatement(Class clazz) {
    StringBuilder sb = new StringBuilder();

    sb.append("create table if not exists ")
        .append(clazz.getSimpleName())
        .append(" (id bigint auto_increment ");

    for(Field field : clazz.getDeclaredFields()) {
      field.setAccessible(true);
      sb.append(", ")
          .append(field.getName())
          .append(" ")
          .append(getDBFieldType(field.getType()));
    }
    sb.append(", primary key (id))");

    return sb.toString();
  }

  private static String getDBFieldType(Class clazz) {
    String result = null;

    if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
      return "int";
    } else if (clazz.equals(long.class) || clazz.equals(Long.class)) {
      return "bigint";
    } else if (clazz.equals(float.class) || clazz.equals(Float.class)
            || clazz.equals(double.class) || clazz.equals(Double.class)) {
        return "double";
    } else if (clazz.equals(String.class)) {
      return "varchar(256)";
    }
    return result;
  }

  public static String getInsertStatement(Object object) {
    Class clazz = object.getClass();
    StringBuilder sb = new StringBuilder();

    sb.append("insert into ")
        .append(clazz.getSimpleName())
        .append("(id");

    for(Field field : clazz.getDeclaredFields()) {
      if (isFieldSQLParticipant.test(field.getType())) {
        sb.append(", ").append(field.getName());
      }
    }

    sb.append(") values (");

    long id = Long.valueOf(ReflectionHelper.geSuperClasstFieldValue(object, "id").toString());
    if (id == 0) {
      sb.append("null");
    } else {
      sb.append(id);
    }

    for(Field field : clazz.getDeclaredFields()) {
      if (isFieldSQLParticipant.test(field.getType())) {
        sb.append(", ");
        if (field.getType().equals(String.class)) {
          sb.append("\'")
              .append(ReflectionHelper.getFieldValue(object, field.getName()).toString())
              .append("\'");
        } else {
          sb.append(ReflectionHelper.getFieldValue(object, field.getName()).toString());
        }
      }
    }
    sb.append(")");

    System.out.println(sb.toString());
    return sb.toString();
  }

  public static String getUpdateStatement(Object object) {
    Class clazz = object.getClass();
    StringBuilder sb = new StringBuilder();

    sb.append("update ")
        .append(clazz.getSimpleName())
        .append(" set ");

    boolean needComma = false;
    for (Field field : clazz.getDeclaredFields()) {
      if (isFieldSQLParticipant.test(field.getType())) {
        if (needComma) {
          sb.append(", ");
        }
        sb.append(field.getName()).append("=");

        if (field.getType().equals(String.class)) {
          sb.append("\'")
              .append(ReflectionHelper.getFieldValue(object, field.getName()).toString())
              .append("\'");
        } else {
          sb.append(ReflectionHelper.getFieldValue(object, field.getName()).toString());
        }
        needComma = true;
      }
    }
    sb.append(" where id=").append(ReflectionHelper.geSuperClasstFieldValue(object, "id"));

    return sb.toString();
  }

  public static Predicate<Class> isFieldSQLParticipant = c -> c.equals(byte.class) || c.equals(Byte.class)
      || c.equals(short.class) || c.equals(Short.class)
      || c.equals(int.class) || c.equals(Integer.class)
      || c.equals(long.class) || c.equals(Long.class)
      || c.equals(String.class);
}
