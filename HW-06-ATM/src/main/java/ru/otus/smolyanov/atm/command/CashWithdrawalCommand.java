package ru.otus.smolyanov.atm.command;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 06 - ATM
 */

import ru.otus.smolyanov.atm.core.Banknote;
import ru.otus.smolyanov.atm.core.CashMachine;
import ru.otus.smolyanov.atm.core.ImpossibleToGiveSpecifiedAmountException;
import ru.otus.smolyanov.atm.core.NotEnoughMoneyException;

import java.util.Collection;
import java.util.Scanner;
import java.io.PrintStream;

public class CashWithdrawalCommand implements Command {

  private final CashMachine cashMachine;
  private final PrintStream printStream;
  private final Scanner scanner;

  public CashWithdrawalCommand(CashMachine cashMachine, PrintStream printStream, Scanner scanner) {
    this.cashMachine = cashMachine;
    this.printStream = printStream;
    this.scanner = scanner;
  }

  @Override
  public void execute() {
    printStream.println("Please type amount: ");

    long amount = scanner.nextLong();
    try {
      Collection<Banknote> banknoteBundle = cashMachine.cashWithdrawal(amount);
      printStream.println("Please take money: "+banknoteBundleToString(banknoteBundle)+"\n");

    } catch (NotEnoughMoneyException | ImpossibleToGiveSpecifiedAmountException e) {
      printStream.println(e.getMessage());
    }
  }

  private String banknoteBundleToString(Collection<Banknote> banknoteBundle) {
    StringBuilder result = new StringBuilder();
    for (Banknote banknote : banknoteBundle) {
      if (result.length() > 0) {
        result.append(" ");
      }
      result.append(banknote.getdDenomination());
    }
    return result.toString();
  }
}
