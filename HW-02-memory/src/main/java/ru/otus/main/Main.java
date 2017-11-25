package ru.otus.main;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 02 - memory measuring
 */

import ru.otus.utils.ObjectsMemoryMeter;

import java.lang.management.ManagementFactory;
import java.util.function.Supplier;

public class Main {
  private static final int arraySize = 5_000_000;
  private static final int sleepTime = 1000;

  public static void main(String[] args) throws InterruptedException {
    System.out.println("-------- Measuring objects and containers memory size ... --------");
    System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName());

    // Object
    ObjectsMemoryMeter objMM = new ObjectsMemoryMeter(
        new Supplier<Object>() {
          @Override
          public Object get() {
            return new Object();
          }
        }, arraySize, sleepTime);
    long memorySize = objMM.getObjectMemorySize();
    System.out.println("Object - "+memorySize+" bytes");

    // Empty String
    objMM = new ObjectsMemoryMeter(
        new Supplier<Object>() {
          @Override
          public Object get() {
            return new String(new char[0]);
          }
        }, arraySize, sleepTime);
    memorySize = objMM.getObjectMemorySize();
    System.out.println("Empty String - "+memorySize+" bytes");

    // Object[0]
    objMM = new ObjectsMemoryMeter(
        new Supplier<Object>() {
          @Override
          public Object get() {
            return new Object[0];
          }
        }, arraySize, sleepTime);
    memorySize = objMM.getObjectMemorySize();
    System.out.println("Empty Array of Object - "+memorySize+" bytes");

    // Object[10]
    objMM = new ObjectsMemoryMeter(
        new Supplier<Object>() {
          @Override
          public Object get() {
            return new Object[10];
          }
        }, arraySize, sleepTime);
    memorySize = objMM.getObjectMemorySize();
    System.out.println("Array of Object size 10 - "+memorySize+" bytes");

    // int[0]
    objMM = new ObjectsMemoryMeter(
        new Supplier<Object>() {
          @Override
          public Object get() {
            return new int[0];
          }
        }, arraySize, sleepTime);
    memorySize = objMM.getObjectMemorySize();
    System.out.println("Empty Array of int - "+memorySize+" bytes");

    // int[10]
    objMM = new ObjectsMemoryMeter(
        new Supplier<Object>() {
          @Override
          public Object get() {
            return new int[10];
          }
        }, arraySize, sleepTime);
    memorySize = objMM.getObjectMemorySize();
    System.out.println("Array of int size 10 - "+memorySize+" bytes");
  }
}
