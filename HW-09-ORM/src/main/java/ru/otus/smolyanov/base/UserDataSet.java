package ru.otus.smolyanov.base;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 09 - my ORM
 */

public class UserDataSet extends DataSet{
  private String name;
  private int age;

  public UserDataSet() {
  }

  public UserDataSet(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  @Override
  public String toString() {
    return "UserDataSet{" +
        "id=" + getId() +
        ",name='" + getName() + '\'' +
        ", age=" + getAge() +
        '}';
  }
}
