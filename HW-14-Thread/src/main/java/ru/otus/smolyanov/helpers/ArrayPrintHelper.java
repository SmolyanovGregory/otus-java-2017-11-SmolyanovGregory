package ru.otus.smolyanov.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Arrays;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 14 - multi-threaded array sorting
 */

public class ArrayPrintHelper {

  private static final Logger logger = LogManager.getLogger();

  public static void printArray(String title, int[] array) {
    logger.info(title);
    logger.info(Arrays.toString(array));
  }

}
