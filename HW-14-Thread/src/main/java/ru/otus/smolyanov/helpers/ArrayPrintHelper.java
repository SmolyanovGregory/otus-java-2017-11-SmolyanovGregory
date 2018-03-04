package ru.otus.smolyanov.helpers;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 14 - multi-threaded array sorting
 */

public class ArrayPrintHelper {

  public static void printArray(String title, int[] array) {
    System.out.println("\n"+title);
    StringBuilder sb = new StringBuilder();

    boolean needComma = false;
    for (int i : array) {
      if (needComma) {
        sb.append(", ");
      } else {
        needComma = true;
      }
      sb.append(i);
    }
    System.out.println(sb.toString());
  }

}
