package ru.otus.smolyanov.atm.core;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 06 - ATM
 */

public class ImpossibleToGiveSpecifiedAmountException extends Exception {
  public ImpossibleToGiveSpecifiedAmountException() {
    super("The ATM cannot issue the specified amount.");
  }
}
