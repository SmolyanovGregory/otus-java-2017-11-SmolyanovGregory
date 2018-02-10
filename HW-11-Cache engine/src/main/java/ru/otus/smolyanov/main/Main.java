package ru.otus.smolyanov.main;

import ru.otus.smolyanov.base.*;
import ru.otus.smolyanov.dbservice.DBService;
import ru.otus.smolyanov.dbservice.DBServiceCachedImpl;

import java.lang.ref.SoftReference;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 11 - My cache engine
 * VM options: -Xmx128m -Xms128m
 */

public class Main {

  public static void main(String ... args) {
    try {
      new Main().run();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  static class BigObject {
    final byte[] array = new byte[1024 * 1024];

    public byte[] getArray() {
      return array;
    }
  }

  private void run() throws Exception{
    /*
      url = "jdbc:h2:tcp://localhost/~/test";
      name = "test";
      pass = "test";
     */

    try(DBService dbService = new DBServiceCachedImpl()) {
      System.out.println("\nCreating users...");

      List<Long> userIdList = new ArrayList<>();

      for (int i = 1; i <= 20; i++ ) {
        List<PhoneDataSet> phonesList = new ArrayList<>();
        phonesList.add(new PhoneDataSet("phone number "+i));

        int age = (int) (Math.random() * 50) + 10;
        UserDataSet user = new UserDataSet("User "+i, age, new AddressDataSet(i+" st"), phonesList);
        dbService.saveUser(user);
        // store user id
        userIdList.add(user.getId());
      }

      System.gc();
      Thread.sleep(1000);

      System.out.println("\nRead all users by id:");
      for (Long id : userIdList) {
        UserDataSet user = dbService.getUser(id);
        System.out.println(user);
      }

      // create many big objects...
      System.out.println("\nCreate many big objects...");
      int size = 500;
      List<SoftReference<BigObject>> references = new ArrayList<>(size);

      for (int k = 0; k < size; k++) {
        references.add(new SoftReference<>(new BigObject()));
      }

      System.gc();
      Thread.sleep(1000);

      System.out.println("\nAnother read all users by id:");
      for (Long id : userIdList) {
        UserDataSet user = dbService.getUser(id);
        System.out.println(user);
      }
    }
  }
}