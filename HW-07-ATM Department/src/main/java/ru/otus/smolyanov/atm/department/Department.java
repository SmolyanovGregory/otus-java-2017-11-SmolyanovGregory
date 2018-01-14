package ru.otus.smolyanov.atm.department;

import ru.otus.smolyanov.atm.core.CashMachine;

import java.util.Iterator;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 07 - ATM Department
 */

public interface Department {

  long getBalance();

  void restoreInitialStateForAll();

  Iterator<CashMachine> getIterator();

  void addCashMachine(CashMachine cashMachine);

  void removeCashMachine(CashMachine cashMachine);

  CashMachine getByNumber(String cashMachineNumber);
}
