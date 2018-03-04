package ru.otus.smolyanov.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 14 - multi-threaded array sorting
 */

public class MultiThreadedArraySorterImpl implements ArraySorter {
  private final int threadCount;

  public MultiThreadedArraySorterImpl(int threadCount) {
    this.threadCount = threadCount;
  }

  public void sort(int[] array) {
    if (array.length < threadCount)
      throw new UnsupportedOperationException("Thread count must be greater or equals then the array length.");

    // creating threads list
    List<Thread> threads = new ArrayList<>();

    // creating subarrays list
    List<int[]> sortedSubArrays = new ArrayList<>();

    // creating threads
    int threadNumber = 0;
    for (int i = 0; i < threadCount; i++) {
       int indexFrom = i* array.length / threadCount;
       int indexTo = (i+1)*array.length / threadCount - 1;

       Thread thread = new ArraySortingThread(Arrays.copyOfRange(array, indexFrom, indexTo + 1), new Callback() {
         @Override
         public void sendResult(int[] array) {
           sortedSubArrays.add(array);
         }
       });
       thread.setName("Sorting thread # "+ (++threadNumber));
       threads.add(thread);
    }

    // sorting arrays
    for(Thread thread : threads) {
      thread.start();
    }

    for(Thread thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    // merge sorted subarrays
    int[] mergedArray = sortedSubArrays.get(0);
    for (int i = 1; i < sortedSubArrays.size(); i++) {
      mergedArray = mergeSortedArrays(mergedArray, sortedSubArrays.get(i));
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
