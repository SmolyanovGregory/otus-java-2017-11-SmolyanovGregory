package ru.otus.main;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 04 - GC
 */

import java.util.ArrayList;
import java.lang.management.ManagementFactory;

public class Main {
  private static final int STATISTICS_PERIOD_LENGTH = 60; // in seconds
  private static final String DEFAULT_LOG_FILE_NAME = "gc_statistics.log";

  public static void main(String...args) {
    String pid = ManagementFactory.getRuntimeMXBean().getName();
    Logger logger = new Logger((args.length > 0 ? args[0] : DEFAULT_LOG_FILE_NAME));
    logger.log("pid = "+pid);

    ArrayList<String> oomArray = new ArrayList<>();

    GCMonitor gcm = new GCMonitor(STATISTICS_PERIOD_LENGTH, logger);
    gcm.start();

    try {
      while (oomArray.size() < Integer.MAX_VALUE) {
        Thread.sleep(1000);

        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
          arrayList.add(new String(new char[0]));
        }

        for (int i = 0; i < 75_000; i++) {
          oomArray.add(new String(new char[0]));
        }
      }

      oomArray.set(0, new String(new char[0]));
    }
    catch (Exception error) {
      logger.log("!!!!!!!!!! Error occured: !!!!!!!!!!");
      logger.log(error.getMessage());
      error.printStackTrace();
    }
  }
}
