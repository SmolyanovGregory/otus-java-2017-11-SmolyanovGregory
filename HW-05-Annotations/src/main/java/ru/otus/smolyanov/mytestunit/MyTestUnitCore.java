package ru.otus.smolyanov.mytestunit;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 05 - annotations
 */

import java.lang.reflect.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import java.io.IOException;
import com.google.common.reflect.ClassPath;
import com.google.common.collect.ImmutableSet;

public class MyTestUnitCore {

  public static void runClassTests(String className) throws ClassNotFoundException {
    Class klass = Class.forName(className);

    Set<String> beforeMethodsNames = new HashSet<>();
    Set<String> testMethodsNames = new HashSet<>();
    Set<String> afterMethodsNames = new HashSet<>();

    Method[] methods = klass.getDeclaredMethods();
    for (Method method : methods) {
      // before
      if (method.isAnnotationPresent(Before.class)) {
        beforeMethodsNames.add(method.getName());
      }
      // test
      if (method.isAnnotationPresent(Test.class)) {
        testMethodsNames.add(method.getName());
      }
      // after
      if (method.isAnnotationPresent(After.class)) {
        afterMethodsNames.add(method.getName());
      }
    }

    // executing tests
    for(String testName : testMethodsNames) {
      Object obj = ReflectionHelper.instantiate(klass);

      // execute before steps
      for(String beforeMethodName : beforeMethodsNames) {
        ReflectionHelper.callMethod(obj, beforeMethodName);
      }

      // execute test
      System.out.println("--------------------");
      callTest(obj, testName);
      System.out.println("--------------------");

      // execute after steps
      for(String afterMethodName : afterMethodsNames) {
        ReflectionHelper.callMethod(obj, afterMethodName);
      }
    }
  }

  public static void runPackageTests(String packageName) throws IOException, ClassNotFoundException {
    Class[] classArray = getPackageClasses(packageName);
    for(Class klass: classArray){
      runClassTests(klass.getName());
    }
  }

  private static Class[] getPackageClasses(String packageName) throws IOException, ClassNotFoundException {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    ClassPath classPath = ClassPath.from(classLoader);
    ImmutableSet<ClassPath.ClassInfo> classInfoSet =  classPath.getTopLevelClasses(packageName);

    Class[] result = new Class[classInfoSet.size()];

    Iterator<ClassPath.ClassInfo> iter = classInfoSet.iterator();
    int i = 0;
    while (iter.hasNext()) {
      result[i] = iter.next().load();
      i++;
    }

    return result;
  }

  private static void callTest(Object object, String name) {
    try {
      ReflectionHelper.callMethod(object, name);
      System.out.println("Test '"+name+"' - OK");
    } catch (Error e) {
      System.out.println("Test '"+name+"' - FAILED");
    }
  }

}
