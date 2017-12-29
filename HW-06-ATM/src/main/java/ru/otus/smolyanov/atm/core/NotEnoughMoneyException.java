package ru.otus.smolyanov.atm.core;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 06 - ATM
 */

public class NotEnoughMoneyException extends Exception {
  public NotEnoughMoneyException() {
    super("The ATM does not have enough money to issue the specified amount.");
  }
}
