package ru.otus.smolyanov.test;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 05 - annotations
 */

import ru.otus.smolyanov.mytestunit.*;

public class TestClass {

  public TestClass() {
  }

  @Before
  public void before() {
    System.out.println("called TestClass.before() method");
  }

  @Before
  public void beforeAnother() {
    System.out.println("called TestClass.beforeAnother() method");
  }

  @Test
  public void doTest1() {
    System.out.println("called TestClass.doTest1() method");
  }

  @Test
  public void doTest2() {
    System.out.println("called TestClass.doTest2() method");
  }

  // not annotated
  public void doTest3() {
    System.out.println("called TestClass.doTest3() method");
  }

  @After
  public void after() {
    System.out.println("called TestClass.after() method");
  }

  @After
  public void afterAnother() {
    System.out.println("called TestClass.afterAnother() method");
  }
}
