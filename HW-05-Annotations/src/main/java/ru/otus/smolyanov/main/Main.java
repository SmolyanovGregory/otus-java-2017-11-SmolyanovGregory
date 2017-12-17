package ru.otus.smolyanov.main;

import ru.otus.smolyanov.mytestunit.MyTestUnitCore;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 05 - annotations
 */

public class Main {

  public static void main(String...args) {
    String className = "ru.otus.smolyanov.test.TestClass";
    String packageName = "ru.otus.smolyanov.test";

    try {
      System.out.println("===== Run tests for class: " + className + " =====");
      MyTestUnitCore.runClassTests(className);

      System.out.println("\n===== Run tests for package: " + packageName + " =====");
      MyTestUnitCore.runPackageTests(packageName);

    }
    catch(Exception e) {
      System.out.println("Error: "+e.getMessage());
      e.printStackTrace();
    }

  }
}
