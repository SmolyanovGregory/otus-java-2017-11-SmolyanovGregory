package ru.otus.smolyanov.util;

import java.util.Arrays;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 14 - multi-threaded array sorting
 */

public class ArraySortingThread extends Thread {

  private int[] array;

  public ArraySortingThread(int[] array) {
    this.array = array;
  }

  @Override
  public void run() {
    super.run();
    sort();
  }

  private void sort() {
    Arrays.sort(array);
  }

  public int[] getArray() {
    return array;
  }
}
