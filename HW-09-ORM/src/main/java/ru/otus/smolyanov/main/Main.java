package ru.otus.smolyanov.main;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 09 - my ORM
 */

import ru.otus.smolyanov.base.UserDataSet;
import ru.otus.smolyanov.base.BankAccountDataSet;
import ru.otus.smolyanov.dbservice.DBService;
import ru.otus.smolyanov.dbservice.DBServiceImpl;

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
    try(DBService dbService = new DBServiceImpl()) {

      System.out.println("Creating users...");
      dbService.save(new UserDataSet("User 1", 18));

      UserDataSet userForEdit = new UserDataSet("User 2", 19);
      dbService.save(userForEdit);

      dbService.save(new UserDataSet("User 3", 20));

      for (UserDataSet user : dbService.getAllUsers()) {
        System.out.println(user.toString());
      }

      System.out.println("\nEdit User 2...");
      userForEdit.setName("Vasya");
      userForEdit.setAge(45);
      dbService.save(userForEdit);

      System.out.println("\nUsers after modification:");
      for (UserDataSet user : dbService.getAllUsers()) {
        System.out.println(user.toString());
      }

      System.out.println("\nCreating bank accounts...");
      BankAccountDataSet account = new BankAccountDataSet("account # 1", "RUR", 123.45);
      dbService.save(account);
      dbService.save(new BankAccountDataSet("account # 2", "USD", 78.9));

      for (BankAccountDataSet bankAccount : dbService.getAllBankAccounts()) {
        System.out.println(bankAccount.toString());
      }

      account = dbService.getBankAccount(account.getId());
      account.setAmount(1_000_000);
      dbService.save(account);

      System.out.println("\nBank accounts after modifications:");
      for (BankAccountDataSet bankAccount : dbService.getAllBankAccounts()) {
        System.out.println(bankAccount.toString());
      }
    }
  }
}