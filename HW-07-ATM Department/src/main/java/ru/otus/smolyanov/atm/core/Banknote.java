package ru.otus.smolyanov.atm.core;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 07 - ATM Department
 */

public enum Banknote {
  FIVE(5),
  TEN(10),
  TWENTY(20),
  FIFTY(50),
  ONE_HUNDRED(100),
  TWO_HUNDRED(200),
  FIVE_HUNDRED(500);

  private final int denomination;

  Banknote(int denomination) {
    this.denomination = denomination;
  }

  public int getdDenomination() {
    return denomination;
  }
}
