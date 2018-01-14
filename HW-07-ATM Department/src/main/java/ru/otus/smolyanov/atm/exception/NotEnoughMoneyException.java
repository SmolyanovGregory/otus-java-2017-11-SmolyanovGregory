package ru.otus.smolyanov.atm.exception;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 07 - ATM Department
 */

public class NotEnoughMoneyException extends Exception {
  public NotEnoughMoneyException() {
    super("The ATM does not have enough money to issue the specified amount.");
  }
}
