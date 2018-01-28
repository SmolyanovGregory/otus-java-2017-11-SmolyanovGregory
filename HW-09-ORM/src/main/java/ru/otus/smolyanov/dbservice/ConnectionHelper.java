package ru.otus.smolyanov.dbservice;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 09 - my ORM
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionHelper {
  static Connection getConnection() {
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
