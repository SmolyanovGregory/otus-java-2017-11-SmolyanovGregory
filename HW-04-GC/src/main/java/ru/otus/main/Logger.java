package ru.otus.main;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 04 - GC
 */

import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;

public class Logger {
  private String logFileName;

  public Logger(String logFileName) {
    this.logFileName = logFileName;
  }

  public void log(String message) {
    System.out.println(message);

    try(FileWriter fw = new FileWriter(logFileName, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw))
    {
      out.println(message);
    } catch (IOException e) {
      System.out.println("Error in log():"+e.getMessage());
      e.printStackTrace();
    }
  }
}
