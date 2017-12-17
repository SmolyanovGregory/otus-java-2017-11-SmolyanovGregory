package ru.otus.smolyanov.mytestunit;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 05 - annotations
 */

public class Assert {

  public static void assertNull(Object object) {
    if (object != null) throw new Error("Expected: <null> but was: " + object.toString());
  }
}
