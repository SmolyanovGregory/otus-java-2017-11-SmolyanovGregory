package ru.otus.smolyanov.main;

import ru.otus.smolyanov.helpers.ArrayPrintHelper;
import ru.otus.smolyanov.util.ArraySorter;
import ru.otus.smolyanov.util.MultiThreadedArraySorterImpl;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 14 - multi-threaded array sorting
 */

public class Main {

  private static final int THREAD_COUNT = 4;

  public static void main(String ... args) {
    try {
      new Main().run();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void run() throws Exception {
    System.out.println("Start...");
    int[] randomIntArray = RandomIntArrayHelper.getRandomIntArray(100_000);
    ArrayPrintHelper.printArray("Unsorted int array:", randomIntArray);

    ArraySorter sorter = new MultiThreadedArraySorterImpl(THREAD_COUNT);
    sorter.sort(randomIntArray);

    ArrayPrintHelper.printArray("Sorted int array:", randomIntArray);
    System.out.println("Unsorted and sorted arrays are saved into result.log");
  }

  // random int array generator
  private static class RandomIntArrayHelper {
    private static final int MAX_NUMBER = 1_000; // max number value

    static int[] getRandomIntArray(int arraySize) {
      int[] result = new int[arraySize];

      for (int i = 0; i < result.length; i++) {
        result[i] = (int) (Math.random() * MAX_NUMBER);
      }

      return result;
    }
  }
}
