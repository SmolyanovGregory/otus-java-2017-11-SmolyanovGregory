package ru.otus.smolyanov.atm.core;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 06 - ATM
 */

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.Comparator;

public class CashMachineImpl implements CashMachine {

  private Map<Banknote, Integer> storage = new HashMap<>();

  public CashMachineImpl() {
  }

  @Override
  public void start() {
    initializeStorage();
  }

  @Override
  public long getBalance() {
    long result = 0;
    for(Banknote banknote : storage.keySet()) {
      result += storage.get(banknote) * banknote.getdDenomination();
    }
    return result;
  }

  @Override
  public Collection<Banknote> cashWithdrawal(long amount) throws NotEnoughMoneyException, ImpossibleToGiveSpecifiedAmountException {
    Collection<Banknote> result = new LinkedList<>();

    if (amount > 0) {
      if (getBalance() >= amount) {
        long collectedSum = 0;
        Object[] storageCells = storage.keySet().stream().sorted(Comparator.reverseOrder()).toArray();

        for (Object obj : storageCells) {
          Banknote banknote = (Banknote) obj;

          if (banknote.getdDenomination() <= amount) {
            int count = storage.get(banknote);
            while (count > 0 && (amount - collectedSum) >= banknote.getdDenomination()) {
              result.add(banknote);
              collectedSum += banknote.getdDenomination();
              count--;
            }

            if (storage.get(banknote) != count)
              storage.put(banknote, count);
          }
        }

        // rollback
        if (collectedSum < amount) {
          for (Banknote banknote : result) {
            storage.put(banknote, storage.get(banknote) + 1);
          }
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
      storage.put(banknote, storage.get(banknote)+1);
    }
  }

  private void initializeStorage(){
    storage = new HashMap<>();

    for(Banknote banknote : Banknote.values()) {
      storage.put(banknote, 0);
    }

  }
}
