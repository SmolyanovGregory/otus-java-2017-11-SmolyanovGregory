package ru.otus.smolyanov.dbservice.myorm.helpers;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 10 - Hibernate ORM
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
  public static Connection getConnection() {
    try {
      String url = "jdbc:h2:tcp://localhost/~/test";
      String name = "test";
      String pass = "test";

      return DriverManager.getConnection(url, name, pass);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
