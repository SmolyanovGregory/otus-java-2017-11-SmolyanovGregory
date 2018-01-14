package ru.otus.smolyanov.atm.core;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 07 - ATM Department
 */

import ru.otus.smolyanov.atm.exception.*;

import java.util.*;

public class CashMachineImpl implements CashMachine {
  private final String number; // cash machine number
  private CashStorage storage;
  private CashStorageMemento savedInitialStorage; // for restore initial state

  public CashMachineImpl(String number) {
    this.number = number;
  }

  @Override
  public long getBalance() {
    long result = 0;
    for(Banknote banknote : Banknote.values()) {
      result += storage.getBanknoteCount(banknote) * banknote.getdDenomination();
    }
    return result;
  }

  @Override
  public Collection<Banknote> cashWithdrawal(long amount) throws NotEnoughMoneyException, ImpossibleToGiveSpecifiedAmountException {
    Collection<Banknote> result = new LinkedList<>();
    CashStorageMemento memento = storage.saveToMemento(); // for rollback

    long remain = amount;

    if (amount > 0) {
      if (getBalance() >= amount) {

        Set<Banknote> cells = new HashSet<>();
        for (Banknote banknote : Banknote.values()) {
          cells.add(banknote);
        }
        Object[] sortedCells = cells.stream().sorted(Comparator.reverseOrder()).toArray();

        for (Object obj : sortedCells) {
          Banknote banknote = (Banknote) obj;

          if (banknote.getdDenomination() <= remain) {
            int countToWithdrawal = Math.min(storage.getBanknoteCount(banknote), (int) remain / banknote.getdDenomination());
            Banknote[] pack = storage.get(banknote, countToWithdrawal);
            for (Banknote currentBanknote : pack) {
              result.add(currentBanknote);
            }
            remain -= pack.length * banknote.getdDenomination();
          }
        }

        // rollback
        if (remain > 0) {
          storage.restoreFromMemento(memento);
          throw new ImpossibleToGiveSpecifiedAmountException();
        }

      } else
        throw new NotEnoughMoneyException();
    } else
      throw new ImpossibleToGiveSpecifiedAmountException();

    return result;
  }

  @Override
  public void cashEntry(Collection<Banknote> banknoteBundle) {
    for (Banknote banknote : banknoteBundle) {
      storage.put(banknote, 1);
    }
  }

  @Override
  public String getNumber() {
    return number;
  }

  @Override
  public void loadCashStorage(CashStorage cashStorage) {
    storage = cashStorage;
    savedInitialStorage = storage.saveToMemento();
  }

  @Override
  public void restoreInitialStorageState() {
    storage.restoreFromMemento(savedInitialStorage);
  }
}
