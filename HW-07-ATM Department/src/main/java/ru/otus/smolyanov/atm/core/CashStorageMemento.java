package ru.otus.smolyanov.atm.core;

import java.util.Map;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 07 - ATM Department
 */

public class CashStorageMemento {

  private final Map<Banknote, Integer> storage;

  public CashStorageMemento(Map<Banknote, Integer> storageToSave) {
    storage = storageToSave;
  }

  public Map<Banknote, Integer> getSavedStorage() {
    return storage;
  }
}
