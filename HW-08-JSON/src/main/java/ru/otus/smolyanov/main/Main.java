package ru.otus.smolyanov.main;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 08 - JSON object writer
 */

import com.google.gson.*;
import ru.otus.smolyanov.yajson.YAJson;

public class Main {

  public static void main(String ... args) {
    try {
      Main main = new Main();
      main.run();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void run() {
    FirstTestClass obj = new FirstTestClass();

    System.out.println("Gson builded Json string:");
    System.out.println(new Gson().toJson(obj));

    System.out.println("My library builded Json string:");
    System.out.println(new YAJson().toJson(obj));

  }
}
