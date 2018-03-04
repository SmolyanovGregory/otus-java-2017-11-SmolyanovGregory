package ru.otus.smolyanov.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 14 - multi-threaded array sorting
 */

public class ArrayPrintHelper {

  private static final Logger logger = LogManager.getLogger();

  public static void printArray(String title, int[] array) {
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
    logger.info(title);
    logger.info(sb.toString());
  }

}
