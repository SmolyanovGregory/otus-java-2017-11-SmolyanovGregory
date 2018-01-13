package ru.otus.smolyanov;

import org.junit.*;
import java.lang.reflect.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import ru.otus.smolyanov.atm.core.Banknote;
import ru.otus.smolyanov.atm.core.CashMachineImpl;
import ru.otus.smolyanov.atm.core.ImpossibleToGiveSpecifiedAmountException;
import ru.otus.smolyanov.atm.core.NotEnoughMoneyException;

public class CoreTest {

  private CashMachineImpl cashMachine;

  @Before
  public void setUp() {
    cashMachine = new CashMachineImpl();
    cashMachine.start();
  }

  @Test
  public void firstTest(){
    Assert.assertNotNull(cashMachine);
  }

  @Test
  public void afterStartStorageSizeShouldBeBanknoteSize() {
    try {
      Field field = cashMachine.getClass().getDeclaredField("storage");
      field.setAccessible(true);
      HashMap<Banknote, Integer> storage = (HashMap<Banknote, Integer>) field.get(cashMachine);

      if (storage.keySet().size() != Banknote.values().length )
        Assert.fail("Wrong storage size");
    } catch (NoSuchFieldException e) {
      Assert.fail("Field 'storage' not found.");
    } catch (IllegalAccessException e) {
      Assert.fail(e.getMessage());
    }
  }

  @Test
  public void initialBalanceShouldBeZero() {
    Assert.assertEquals(cashMachine.getBalance(), 0);
  }

  @Test
  public void balanceShouldBe685() {
    cashEntry685();
    Assert.assertEquals(cashMachine.getBalance(), 685);
  }

  @Test
  public void balanceShouldBe85() {
    cashEntry685();
    try {
      Collection moneyPack = cashMachine.cashWithdrawal(600);
    } catch (NotEnoughMoneyException | ImpossibleToGiveSpecifiedAmountException e) {
      Assert.fail(e.getMessage());
    }

    Assert.assertTrue(cashMachine.getBalance() == 85);
  }

  private void cashEntry685() {
    Collection<Banknote> moneyPack = new LinkedList<>();

    moneyPack.add(Banknote.FIVE_HUNDRED);
    moneyPack.add(Banknote.ONE_HUNDRED);
    moneyPack.add(Banknote.FIFTY);
    moneyPack.add(Banknote.TWENTY);
    moneyPack.add(Banknote.TEN);
    moneyPack.add(Banknote.FIVE);

    cashMachine.cashEntry(moneyPack);
  }

  @Test
  public void testWithdrawalIllegalAmount() {
    cashEntry685();

    try {
      Collection moneyPack = cashMachine.cashWithdrawal(2);
    } catch (ImpossibleToGiveSpecifiedAmountException e) {
      Assert.assertTrue(cashMachine.getBalance() == 685);
      return;
    }
    catch (NotEnoughMoneyException e) {
      Assert.fail(e.getMessage());
      return;
    }
    Assert.fail("Illegal amount accepted!");
  }

  @Test
  public void testNotEnoughMoneyException() {
    cashEntry685();

    try {
      Collection moneyPack = cashMachine.cashWithdrawal(700);
    } catch (NotEnoughMoneyException e) {
      return;
    } catch (ImpossibleToGiveSpecifiedAmountException e) {
      Assert.fail(e.getMessage());
    }
    Assert.fail("NotEnoughMoneyException not generated.");
  }

  @Test
  public void testImpossibleToGiveSpecifiedAmountException() {
    cashEntry685();

    try {
      Collection moneyPack = cashMachine.cashWithdrawal(40);
    } catch (NotEnoughMoneyException e) {
      Assert.fail(e.getMessage());
    } catch (ImpossibleToGiveSpecifiedAmountException e) {
      return;
    }
    Assert.fail("ImpossibleToGiveSpecifiedAmountException not generated.");
  }

  @Test
  public void testNegativeAmount() {
    cashEntry685();

    try {
      Collection moneyPack = cashMachine.cashWithdrawal(-40);
    } catch (NotEnoughMoneyException e) {
      Assert.fail(e.getMessage());
    } catch (ImpossibleToGiveSpecifiedAmountException e) {
      return;
    }
    Assert.fail("ImpossibleToGiveSpecifiedAmountException not generated.");
  }

  @Test
  public void testBanknoteDenomination() {
    Assert.assertTrue(Banknote.FIVE.getdDenomination() == Long.valueOf("5"));
    Assert.assertTrue(Banknote.TEN.getdDenomination() == Long.valueOf("10"));
    Assert.assertTrue(Banknote.TWENTY.getdDenomination() == Long.valueOf("20"));
    Assert.assertTrue(Banknote.FIFTY.getdDenomination() == Long.valueOf("50"));
    Assert.assertTrue(Banknote.ONE_HUNDRED.getdDenomination() == Long.valueOf("100"));
    Assert.assertTrue(Banknote.FIVE_HUNDRED.getdDenomination() == Long.valueOf("500"));
  }
}
