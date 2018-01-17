package ru.otus.smolyanov.main;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 07 - ATM Department
 */

import ru.otus.smolyanov.atm.core.*;
import ru.otus.smolyanov.atm.department.*;
import ru.otus.smolyanov.atm.exception.*;

import java.util.Collection;
import java.util.LinkedList;

public class Main {

  private void run() {
    Department department = new DepartmentImpl();

    // ATM creating
    for (int num = 1; num <= 3; num++) {
      CashMachine cashMachine = new CashMachineImpl("Number "+num);
      department.addCashMachine(cashMachine);

      // cash storage initializing
      CashStorage cashStorage = new CashStorage();
      for (Banknote banknote : Banknote.values()) {
        cashStorage.put(banknote, num * 10);
      }
      // load the cartridge with a cash
      cashMachine.loadCashStorage(cashStorage);
    }

    System.out.println("Initial department balance = " + department.getBalance());

    long amount = 1_800;
    try {
      Collection<Banknote> cashPack = department.getByNumber("Number 2").cashWithdrawal(amount);
      System.out.println("******** Money pack as cash withdrawal result of "+amount+" in the ATM Number 2: ********");
      for (Banknote banknote : cashPack) {
        System.out.println(banknote.getdDenomination());
      }
      System.out.println("*****************************");
    } catch (NotEnoughMoneyException | ImpossibleToGiveSpecifiedAmountException e) {
      System.out.println(e.getMessage());
    }

    long anotherAmount = 2_430;
    try {
      Collection<Banknote> cashPack = department.getByNumber("Number 3").cashWithdrawal(anotherAmount);
      System.out.println("******** Money pack as cash withdrawal result of "+anotherAmount+" in the ATM Number 3: ********");
      for (Banknote banknote : cashPack) {
        System.out.println(banknote.getdDenomination());
      }
      System.out.println("*****************************");

    } catch (NotEnoughMoneyException | ImpossibleToGiveSpecifiedAmountException e) {
      System.out.println(e.getMessage());
    }

    long thirdAmount = 600;
    try {
      Collection<Banknote> cashPack = department.getByNumber("Number 1").cashWithdrawal(thirdAmount);

      System.out.println("******** Money pack as cash withdrawal result of "+thirdAmount+" in the ATM Number 1: ********");
      for (Banknote banknote : cashPack) {
        System.out.println(banknote.getdDenomination());
      }
      System.out.println("*****************************");
    } catch (NotEnoughMoneyException | ImpossibleToGiveSpecifiedAmountException e) {
      System.out.println(e.getMessage());
    }

    System.out.println("Department balance after withdrawal of "+(amount+anotherAmount+thirdAmount)+" = " + department.getBalance());




    // cash entry
    Collection<Banknote> moneyPack = new LinkedList<>();
    moneyPack.add(Banknote.FIVE_HUNDRED);
    moneyPack.add(Banknote.TEN);

    department.getByNumber("Number 1").cashEntry(moneyPack);

    System.out.println("Department balance after cash entry = "+ department.getBalance());

    System.out.println("Restore initial ATM state...");
    department.restoreInitialStateForAll();
    System.out.println("Department balance after restore initial state = "+department.getBalance());
  }

  public static void main(String ... args) {
    try {
      Main main = new Main();
      main.run();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
