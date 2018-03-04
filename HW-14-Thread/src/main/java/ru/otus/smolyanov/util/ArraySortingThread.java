package ru.otus.smolyanov.util;

import java.util.Arrays;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 14 - multi-threaded array sorting
 */

public class ArraySortingThread extends Thread {

  private int[] array;
  private Callback callback;

  public ArraySortingThread(int[] array, Callback callback) {
    this.array = array;
    this.callback = callback;
  }

  @Override
  public void run() {
    super.run();
    sort();
    callback.sendResult(array);
  }

  private void sort() {
    Arrays.sort(array);
  }

}
