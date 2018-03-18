package ru.otus.smolyanov.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 15 (message system)
 */

public class PropertiesHelper {

  public static Properties getProperies(String propertiesName) {
    Properties result = new Properties();

    try {
      InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesName);
      result.load(inputStream);
      inputStream.close();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

    return result;
  }
}
