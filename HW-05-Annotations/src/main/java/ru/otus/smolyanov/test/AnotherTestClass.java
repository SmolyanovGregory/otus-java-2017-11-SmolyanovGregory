package ru.otus.smolyanov.test;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 05 - annotations
 */

import ru.otus.smolyanov.mytestunit.*;

public class AnotherTestClass {

  public AnotherTestClass() {
  }

  @Before
  public void setup() {
    System.out.println("called AnotherTestClass.setup() method annotated as @Before");
  }

  @Test
  public void testSomething() {
    System.out.println("called AnotherTestClass.testSomething() method annotated as @Test");
    Assert.assertNull(new Object());
  }

  // not annotated
  public void anotherTestSomething(){
    System.out.println("called AnotherTestClass.anotherTestSomething() method annotated as @Test");
  }

  @After
  public void dispose() {
    System.out.println("called AnotherTestClass.dispose() method annotated as @After");
  }
}
