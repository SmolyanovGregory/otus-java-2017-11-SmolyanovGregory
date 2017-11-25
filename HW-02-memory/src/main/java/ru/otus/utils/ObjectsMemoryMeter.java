package ru.otus.utils;

/**
 * Created by Gregory Smolyanov.
 *
 * Home work 02 - memory measuring
 */

import java.util.function.Supplier;

public class ObjectsMemoryMeter {

  private Supplier supplier;
  private int arraySize;
  private int sleepTime;

  public ObjectsMemoryMeter(Supplier<Object> supplier, int arraySize, int sleepTime) {
    this.supplier = supplier;
    this.arraySize = arraySize;
    this.sleepTime = sleepTime;
  }

  public long getObjectMemorySize() throws InterruptedException {
    Object[] objectArray = new Object[arraySize];
    long memoryBefore = 0;
    long memoryAfter = 0;

    Runtime runtime = Runtime.getRuntime();

    System.gc();
    Thread.sleep(sleepTime);

    memoryBefore = runtime.totalMemory() - runtime.freeMemory();

    for (int i = 0; i < arraySize; i++) {
      objectArray[i] = supplier.get();
    }

    memoryAfter = runtime.totalMemory() - runtime.freeMemory();

    return (memoryAfter - memoryBefore)/arraySize;
  }
}
