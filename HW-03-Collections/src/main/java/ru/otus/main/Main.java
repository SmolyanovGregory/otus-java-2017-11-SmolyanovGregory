package ru.otus.main;

import ru.otus.utils.*;
import java.util.*;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 03 - ArrayList realisation
 */

public class Main {
  private static final int INITIAL_LIST_SIZE = 16;

  private static void printListElements(List list) {
    if (list != null) {
      StringBuffer sb = new StringBuffer();
      sb.append("[");
      for(Object obj: list.toArray()) {
        if (sb.toString().compareTo("[")!=0)
          sb.append(", ");
        sb.append(obj);
      }
      sb.append("]");
      System.out.println(sb.toString());
    } else {
      System.out.println("Error: List is null");
    }
  }

  public static void main(String[] args) {
    List<Integer> myList = new MyArrayList<Integer>();

    // List initializing
    Random rnd = new Random();
    for (int i = 1; i <= INITIAL_LIST_SIZE; i++) {
      myList.add(rnd.nextInt(100));
    }

    System.out.println("List initialized by values:");
    printListElements(myList);

    // List sorting
    Collections.sort(myList, (integer, t1) ->
        Integer.compare(integer, t1)
    );
    System.out.println("List elements after sorting:");
    printListElements(myList);

    // List copying
    List<Integer> arrList = new ArrayList<>();
    for (int i = 0; i < myList.size(); i++) arrList.add(0);
    Collections.copy(arrList, myList);
    System.out.println("Copied list elements:");
    printListElements(arrList);

    // List enlargement
    System.out.println("List elements after adding any items:");
    Collections.addAll(myList, 1111, 2222, 3333 ,4444, 5555);
    printListElements(myList);

  }
}
