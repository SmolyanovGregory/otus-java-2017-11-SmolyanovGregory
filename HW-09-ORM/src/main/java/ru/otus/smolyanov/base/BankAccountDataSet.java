package ru.otus.smolyanov.base;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 09 - my ORM
 */

public class BankAccountDataSet extends DataSet {

  private String accountNumber;
  private String currencyCode;
  private double amount;

  public BankAccountDataSet() {
  }

  public BankAccountDataSet(String accountNumber, String currencyCode, double amount) {
    this.accountNumber = accountNumber;
    this.currencyCode = currencyCode;
    this.amount = amount;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  @Override
  public String toString() {
    return "BankAccountDataSet{" +
        "id=" + getId() +
        ",accountNumber='" + getAccountNumber() + '\'' +
        ", currencyCode='" + getCurrencyCode() + '\'' +
        ", amount=" + getAmount() +
        '}';
  }
}
