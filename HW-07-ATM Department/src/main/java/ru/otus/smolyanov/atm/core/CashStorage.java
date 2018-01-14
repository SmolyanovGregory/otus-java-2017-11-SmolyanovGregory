package ru.otus.smolyanov.atm.core;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 07 - ATM Department
 */

import java.util.HashMap;
import java.util.Map;

// Originator
public class CashStorage {
  private Map<Banknote, Integer> storage = new HashMap<>();

  public Banknote[] get(Banknote banknote, int count) {
    int currentCount = storage.get(banknote);

    if (count > currentCount)
      throw new RuntimeException("Can not get " + count + " of denomination " + banknote.getdDenomination());

    storage.put(banknote, currentCount - count);

    return new Banknote[count];
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
