package ru.otus.smolyanov.main;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 10 - Hibernate ORM
 */

import ru.otus.smolyanov.base.*;
import ru.otus.smolyanov.dbservice.DBService;
import ru.otus.smolyanov.dbservice.hibernate.DBServiceHibernateImpl;
import ru.otus.smolyanov.dbservice.myorm.DBServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {

  public static void main(String ... args) {
    try {
      new Main().run();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void run() throws Exception{
    /*
      url = "jdbc:h2:tcp://localhost/~/test";
      name = "test";
      pass = "test";
     */

    //try(DBService dbService = new DBServiceImpl()) {
    try(DBService dbService = new DBServiceHibernateImpl()) {
      System.out.println("Creating users...");

      List<PhoneDataSet> phonesList = new ArrayList<>();
      phonesList.add(new PhoneDataSet("123-45-67"));
      phonesList.add(new PhoneDataSet("987-65-43"));

      dbService.saveUser(new UserDataSet("User 1", 18, new AddressDataSet("Abrikosovaya str"), phonesList));

      UserDataSet userForEdit = new UserDataSet("User 2", 19, new AddressDataSet("Vinogradnaya str"), null);
      dbService.saveUser(userForEdit);

      dbService.saveUser(new UserDataSet("User 3", 20, new AddressDataSet("Tenistaya str"), null));

      for (UserDataSet user : dbService.getAllUsers()) {
        System.out.println(user.toString());
      }

      System.out.println("\nEdit User 2...");
      userForEdit.setName("Vasya");
      userForEdit.setAge(45);

      List<PhoneDataSet> anotherPhonesList = new ArrayList<>();
      anotherPhonesList.add(new PhoneDataSet("555-44-33"));
      anotherPhonesList.add(new PhoneDataSet("555-11-22"));
      userForEdit.setPhones(anotherPhonesList);

      dbService.saveUser(userForEdit);

      System.out.println("\nUsers after modification:");
      for (UserDataSet user : dbService.getAllUsers()) {
        System.out.println(user.toString());
      }
    }
  }
}