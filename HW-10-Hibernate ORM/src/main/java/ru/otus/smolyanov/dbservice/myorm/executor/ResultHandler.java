package ru.otus.smolyanov.dbservice.myorm.executor;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 10 - Hibernate ORM
 */

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultHandler<T> {
  T handle(ResultSet result) throws SQLException;
}
