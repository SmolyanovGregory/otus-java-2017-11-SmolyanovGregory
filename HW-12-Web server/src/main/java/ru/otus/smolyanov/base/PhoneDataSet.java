package ru.otus.smolyanov.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 12 - Web server
 */

@Entity
@Table(name = "PHONE")
public class PhoneDataSet extends DataSet{

  @Column(name = "number")
  private String number;

  public PhoneDataSet() {}

  public PhoneDataSet(String number) {
    this.number = number;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  @Override
  public String toString() {
    return "PhoneDataSet{" +
        "id="+getId() + '\'' +
        "number='" + number + '\'' +
        '}';
  }
}
