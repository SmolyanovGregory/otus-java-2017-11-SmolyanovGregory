package ru.otus.smolyanov.main;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 08 - JSON object writer
 */

import java.util.*;

public class FirstTestClass {
  private int intValue = 1;
  private Integer nullValue = null;
  private String stringValue = "abc";
  private transient int value3 = 3;
  private double doubleValue = 123.456;
  private boolean boolValue = true;
  private char charValue = 'A';
  private int[] arrayOfInt = {1,2,3,4,5};
  private String[] arrayOfString = {"aa",null,"cc"};
  private List<Integer> listOfInteger = Arrays.asList(7,null,9);
  private Set setOfString = new HashSet();
  private SecondTestClass secondTestClass = new SecondTestClass();

  public FirstTestClass() {
    setOfString.add("xx");
    setOfString.add(null);
    setOfString.add("zz");
  }
}
