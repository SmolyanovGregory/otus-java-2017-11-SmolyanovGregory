package ru.otus.smolyanov.atm.core;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 07 - ATM Department
 */

import edu.emory.mathcs.backport.java.util.Arrays;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Collection;

// Originator
public class CashStorage {
  private Map<Banknote, Integer> storage = new HashMap<>();

  public Collection<Banknote> get(Banknote banknote, int count) {
    int currentCount = storage.get(banknote);

    if (count > currentCount)
      throw new RuntimeException("Can not get " + count + " of denomination " + banknote.getdDenomination());

    storage.put(banknote, currentCount - count);

    Collection<Banknote> result = new LinkedList<>();
    if (count > 0) {
      for (int i = 1; i <= count; i++) {
        result.add(banknote);
      }
    }

    return result;
  }

  public void put(Banknote banknote, int count) {
    storage.put(banknote, storage.getOrDefault(banknote, 0) + count);
  }

  public int getBanknoteCount(Banknote banknote) {
    return storage.getOrDefault(banknote, 0);
  }

  public CashStorageMemento saveToMemento() {
    Map<Banknote, Integer> storageClone = new HashMap<>();
    for (Banknote banknote : storage.keySet()) {
      storageClone.put(banknote, storage.get(banknote));
    }
    return new CashStorageMemento(storageClone);
  }

  public void restoreFromMemento(CashStorageMemento memento) {
    storage = memento.getSavedStorage();
  }
}
