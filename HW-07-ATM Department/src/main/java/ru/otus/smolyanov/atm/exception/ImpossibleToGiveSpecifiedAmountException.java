package ru.otus.smolyanov.atm.exception;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 07 - ATM Department
 */

public class ImpossibleToGiveSpecifiedAmountException extends Exception {
  public ImpossibleToGiveSpecifiedAmountException() {
    super("The ATM cannot issue the specified amount.");
  }
}
