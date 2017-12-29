package ru.otus.smolyanov.atm.command;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 06 - ATM
 */

public class CommandInvoker {

  private final Command getBalanceCommand;
  private final Command cashWithdrawalCommand;
  private final Command cashEntryCommand;

  public CommandInvoker(Command getBalanceCommand, Command cashWithdrawalCommand, Command cashEntryCommand) {
    this.getBalanceCommand = getBalanceCommand;
    this.cashWithdrawalCommand = cashWithdrawalCommand;
    this.cashEntryCommand = cashEntryCommand;
  }

  public void getBalance(){
    getBalanceCommand.execute();
  }

  public void cashWithdrawal() {
    cashWithdrawalCommand.execute();
  }

  public void cashEntry() {
    cashEntryCommand.execute();
  }
}
