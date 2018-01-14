package ru.otus.smolyanov.main;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 07 - ATM Department
 */

import java.util.LinkedList;
import ru.otus.smolyanov.atm.core.*;
import ru.otus.smolyanov.atm.department.*;
import ru.otus.smolyanov.atm.exception.*;

import java.util.Collection;

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

    long amount = 10_800;
    try {
      Collection<Banknote> cashPack = department.getByNumber("Number 2").cashWithdrawal(amount);
    } catch (NotEnoughMoneyException | ImpossibleToGiveSpecifiedAmountException e) {
      System.out.println(e.getMessage());
    }

    long anotherAmount = 12_410;
    try {
      Collection<Banknote> cashPack = department.getByNumber("Number 3").cashWithdrawal(anotherAmount);
    } catch (NotEnoughMoneyException | ImpossibleToGiveSpecifiedAmountException e) {
      System.out.println(e.getMessage());
    }

    System.out.println("Department balance after withdrawal of "+(amount+anotherAmount)+" = " + department.getBalance());

    // cash entry
    Collection<Banknote> moneyPack = new java.util.LinkedList<>();
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
