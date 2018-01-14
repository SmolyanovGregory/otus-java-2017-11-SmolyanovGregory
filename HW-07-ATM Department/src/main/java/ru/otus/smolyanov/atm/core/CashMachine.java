package ru.otus.smolyanov.atm.core;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 07 - ATM Department
 */

import ru.otus.smolyanov.atm.exception.ImpossibleToGiveSpecifiedAmountException;
import ru.otus.smolyanov.atm.exception.NotEnoughMoneyException;

import java.util.Collection;

public interface CashMachine {
  // узнать баланс
  long getBalance();

  // снять наличные
  Collection<Banknote> cashWithdrawal(long amount) throws NotEnoughMoneyException, ImpossibleToGiveSpecifiedAmountException;

  // внести наличные
  void cashEntry(Collection<Banknote> banknoteBundle);

  // возвращает уникальный номер банкомата
  String getNumber();

  // загрузить блок кассет с наличными
  void loadCashStorage(CashStorage cashStorage);

  // восстановить первоначальное состояние кассет с наличными
  void restoreInitialStorageState();
}
