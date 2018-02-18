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
@Table(name = "ADDRESS")
public class AddressDataSet extends DataSet {

  @Column(name = "street")
  private String street;

  public AddressDataSet() {}

  public AddressDataSet(String street) {
    this.street = street;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  @Override
  public String toString() {
    return "AddressDataSet{" +
        "id="+getId() + '\'' +
        "street='" + street + '\'' +
        '}';
  }
}
