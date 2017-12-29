package ru.otus.smolyanov.atm.command;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 06 - ATM
 */

import ru.otus.smolyanov.atm.core.Banknote;
import ru.otus.smolyanov.atm.core.CashMachine;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.PrintStream;
import java.util.StringTokenizer;

public class CashEntryCommand implements Command {
  private final CashMachine cashMachine;
  private final PrintStream printStream;
  private final Scanner scanner;

  public CashEntryCommand(CashMachine cashMachine, PrintStream printStream, Scanner scanner) {
    this.cashMachine = cashMachine;
    this.printStream = printStream;
    this.scanner = scanner;
  }

  @Override
  public void execute() {
    printStream.println("Please entry cash: ");

    Collection<String> notSupportedBanknotes = new LinkedList<>();
    Collection<Banknote> banknoteBundle = new LinkedList<>();
    StringTokenizer tokenizer = new StringTokenizer(scanner.nextLine());

    while (tokenizer.hasMoreTokens()) {
      String token = tokenizer.nextToken();

      boolean isValidDenomination = false;
      for (Banknote banknote : Banknote.values()) {
        if (banknote.getdDenomination() == Long.valueOf(token)) {
          banknoteBundle.add(banknote);
          isValidDenomination = true;
        }
      }
      if (!isValidDenomination)
        notSupportedBanknotes.add(token);
    }

    cashMachine.cashEntry(banknoteBundle);

    if (!notSupportedBanknotes.isEmpty()) {
      StringBuilder sb = new StringBuilder();
      sb.append("Banknotes of the following denominations are not accepted:");
      for(String banknote: notSupportedBanknotes) {
        sb.append(" ").append(banknote);
      }
      printStream.println(sb.toString());
    }
  }
}
