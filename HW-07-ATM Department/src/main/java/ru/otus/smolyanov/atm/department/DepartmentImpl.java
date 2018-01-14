package ru.otus.smolyanov.atm.department;

import ru.otus.smolyanov.atm.core.CashMachine;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 07 - ATM Department
 */

public class DepartmentImpl implements Department{

  private Map<String, CashMachine> cashMachines = new HashMap<>();

  @Override
  public long getBalance() {
    return cashMachines.values().stream().mapToLong(entry -> entry.getBalance()).sum();
  }

  @Override
  public void restoreInitialStateForAll() {
    for (CashMachine cashMachine : cashMachines.values()) {
      cashMachine.restoreInitialStorageState();
    }
  }

  @Override
  public Iterator<CashMachine> getIterator() {
    return cashMachines.values().iterator();
  }

  @Override
  public void addCashMachine(CashMachine cashMachine) {
    cashMachines.put(cashMachine.getNumber(), cashMachine);
  }

  @Override
  public void removeCashMachine(CashMachine cashMachine) {
    cashMachines.remove(cashMachine.getNumber());
  }

  @Override
  public CashMachine getByNumber(String cashMachineNumber) {
    return cashMachines.get(cashMachineNumber);
  }
}
