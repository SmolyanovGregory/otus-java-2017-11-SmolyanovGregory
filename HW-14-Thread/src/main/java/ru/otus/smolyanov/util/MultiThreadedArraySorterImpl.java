package ru.otus.smolyanov.util;

import java.util.Arrays;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 14 - multi-threaded array sorting
 */

public class MultiThreadedArraySorterImpl implements ArraySorter {
  private final int threadCount;
  private ArraySortingThread[] threads;

  public MultiThreadedArraySorterImpl(int threadCount) {
    this.threadCount = threadCount;
  }

  public void sort(int[] array) {
    if (array.length < threadCount)
      throw new UnsupportedOperationException("Thread count must be greater or equals then the array length.");

    // creating a thread array
    threads = new ArraySortingThread[threadCount];

    // creating threads
    int threadNumber = 0;
    for (int i = 0; i < threadCount; i++) {
       int indexFrom = i* array.length / threadCount;
       int indexTo = (i+1)*array.length / threadCount - 1;

       ArraySortingThread thread = new ArraySortingThread(Arrays.copyOfRange(array, indexFrom, indexTo+1));
       thread.setName("Sorting thread # "+ (++threadNumber));
       threads[i] = thread;
    }

    for(ArraySortingThread thread : threads) {
      thread.start();
    }

    for(ArraySortingThread thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    // merge sorted subarrays
    int[] mergedArray = threads[0].getArray();
    for (int i = 1; i < threads.length; i++) {
      mergedArray = mergeSortedArrays(mergedArray, threads[i].getArray());
    }

    System.arraycopy(mergedArray, 0, array, 0, mergedArray.length);
  }

  private int[] mergeSortedArrays(int[] firstArray, int[] secondArray) {
    int[] result = new int[firstArray.length + secondArray.length];

    int firstArrayIndex = 0;
    int secondArrayIndex = 0;
    int resultArrayIndex = 0;

    while (resultArrayIndex < result.length) {

      if (firstArrayIndex < firstArray.length && secondArrayIndex <  secondArray.length) {
        if (firstArray[firstArrayIndex] <= secondArray[secondArrayIndex]) {
          result[resultArrayIndex] = firstArray[firstArrayIndex++];
        } else {
          result[resultArrayIndex] = secondArray[secondArrayIndex++];
        }
      } else {
        if (firstArrayIndex == firstArray.length) {
          result[resultArrayIndex] = secondArray[secondArrayIndex++];
        } else if (secondArrayIndex == secondArray.length) {
          result[resultArrayIndex] = firstArray[firstArrayIndex++];
        }
      }

      resultArrayIndex++;
    }
    return result;
  }
}
