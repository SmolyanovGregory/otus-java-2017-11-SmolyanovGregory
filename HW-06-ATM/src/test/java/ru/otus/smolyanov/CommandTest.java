package ru.otus.smolyanov;

import org.junit.*;
import ru.otus.smolyanov.atm.core.CashMachineImpl;
import ru.otus.smolyanov.atm.command.*;

import java.io.*;

public class CommandTest {

  private CashMachineImpl cashMachine;

  @Before
  public void setUp() {
    cashMachine = new CashMachineImpl();
    cashMachine.start();
  }

  @Test
  public void getBalanceCommandTest() {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();

    GetBalanceCommand getBalanceCommand = new GetBalanceCommand(cashMachine, new PrintStream(bos));
    getBalanceCommand.execute();

    Assert.assertTrue(bos.toString().substring(0,18).compareTo("Current balance: 0") == 0);
  }
}
