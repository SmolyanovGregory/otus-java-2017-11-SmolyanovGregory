import org.junit.*;
import org.junit.rules.ExpectedException;
import ru.otus.smolyanov.atm.core.*;
import ru.otus.smolyanov.atm.exception.ImpossibleToGiveSpecifiedAmountException;
import ru.otus.smolyanov.atm.exception.NotEnoughMoneyException;

import java.util.Collection;
import java.util.LinkedList;


public class TestCore {

  private CashMachine cashMachine;

  @Before
  public void setUp() {
    cashMachine = new CashMachineImpl("test number");
    CashStorage cashStorage = new CashStorage();
    for (Banknote banknote : Banknote.values()) {
      cashStorage.put(banknote, 1);
    }
    cashMachine.loadCashStorage(cashStorage);
  }

  @Test
  public void cashMachineShouldBeNotNull() {
    Assert.assertNotNull(cashMachine);
  }

  @Test
  public void testATMFullSum() {
    long sum = 0;
    for (Banknote banknote : Banknote.values()) {
      sum += banknote.getdDenomination();
    }
    Assert.assertEquals(sum, cashMachine.getBalance());
  }

  @Test
  public void testCashStorageCapasity() {
    CashStorage cashStorage = new CashStorage();
    cashStorage.put(Banknote.FIVE_HUNDRED, 10);
    cashStorage.put(Banknote.FIVE, 1);
    Assert.assertEquals(10, cashStorage.get(Banknote.FIVE_HUNDRED, 10).length);
    Assert.assertEquals(1, cashStorage.get(Banknote.FIVE, 1).length);
  }

  @Test
  public void testCashMachineNumber() {
    Assert.assertEquals("test number", cashMachine.getNumber());
  }

  @Test
  public void testCashWithdrawal() {
    long amountBefore = cashMachine.getBalance();
    try {
      Collection<Banknote> pack = cashMachine.cashWithdrawal(600);
      Assert.assertEquals(amountBefore-600, cashMachine.getBalance());

    } catch (NotEnoughMoneyException | ImpossibleToGiveSpecifiedAmountException e) {
      e.printStackTrace();
      Assert.fail();
    }
  }

  @Test
  public void testCashWithdrawalWithError() {
    long amountBefore = cashMachine.getBalance();
    try {
      Collection<Banknote> pack = cashMachine.cashWithdrawal(123);
    } catch (NotEnoughMoneyException | ImpossibleToGiveSpecifiedAmountException e) {
      Assert.assertEquals(amountBefore, cashMachine.getBalance());
    }
  }

  @Test
  public void testNotEnoughMoneyException() {
    long amountBefore = cashMachine.getBalance();
    try {
      Collection<Banknote> pack = cashMachine.cashWithdrawal(100_000);
    } catch (NotEnoughMoneyException e) {
      Assert.assertEquals(amountBefore, cashMachine.getBalance());
    }
    catch (ImpossibleToGiveSpecifiedAmountException e) {
      Assert.fail();
    }
  }

  @Test
  public void testRestoreInitialState() {
    long amountBefore = cashMachine.getBalance();
    try {
      Collection<Banknote> pack = cashMachine.cashWithdrawal(500);
    } catch (NotEnoughMoneyException | ImpossibleToGiveSpecifiedAmountException e) {
      Assert.fail();
    }
    cashMachine.restoreInitialStorageState();
    Assert.assertEquals(amountBefore, cashMachine.getBalance());
  }

  @Test
  public void testImpossibleToGiveSpecifiedAmount() {
    try {
      Collection<Banknote> pack = cashMachine.cashWithdrawal(1);
      Assert.fail();
    } catch (NotEnoughMoneyException e) {
      Assert.fail();
    }
    catch (ImpossibleToGiveSpecifiedAmountException e) {
      Assert.assertTrue(true);
    }
  }

  @Test
  public void testCashEntry() {
    long amountBefore = cashMachine.getBalance();

    Collection<Banknote> pack = new LinkedList<>();
    pack.add(Banknote.ONE_HUNDRED);
    cashMachine.cashEntry(pack);

    Assert.assertEquals(amountBefore+100, cashMachine.getBalance());
  }

}
