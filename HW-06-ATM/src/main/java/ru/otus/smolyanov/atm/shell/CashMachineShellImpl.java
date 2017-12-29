package ru.otus.smolyanov.atm.shell;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 06 - ATM
 */

import ru.otus.smolyanov.atm.command.*;
import ru.otus.smolyanov.atm.core.CashMachine;
import java.io.PrintStream;
import java.io.InputStream;
import java.util.Scanner;

public class CashMachineShellImpl implements CashMachineShell {

  private final String COMMAND_BALANCE = "1";
  private final String COMMAND_WITHDRAWAL = "2";
  private final String COMMAND_ENTRY = "3";

  private final Scanner scanner;
  private final PrintStream printStream;
  private final CashMachine cashMachine;
  private CommandInvoker commandInvoker;

  public CashMachineShellImpl(CashMachine cashMachine, InputStream inputStream, PrintStream printStream) {
    this.cashMachine = cashMachine;
    scanner = new Scanner(inputStream);
    this.printStream = printStream;

    commandInvoker = new CommandInvoker(
      new GetBalanceCommand(cashMachine, printStream),
      new CashWithdrawalCommand(cashMachine, printStream, inputStream),
      new CashEntryCommand(cashMachine, printStream, inputStream)
    );
  }

  @Override
  public void start() {
    cashMachine.start();

    while (true) {
      // start menu
      printStream.println("------------------------------\n" +
          "Type '"+COMMAND_BALANCE+"' for show the balance\n" +
          "Type '"+COMMAND_WITHDRAWAL+"' for withdrawal cash\n" +
          "Type '"+COMMAND_ENTRY+"' for entry cash"+
          "\n------------------------------");

      String command = scanner.nextLine();
      switch (command) {
        case COMMAND_BALANCE:
          commandInvoker.getBalance();
          break;
        case COMMAND_WITHDRAWAL:
          commandInvoker.cashWithdrawal();
          break;
        case COMMAND_ENTRY:
          commandInvoker.cashEntry();
          break;
        default:
          printStream.println("Incorrect command: "+command);
          break;
      }
    }
  }
}
