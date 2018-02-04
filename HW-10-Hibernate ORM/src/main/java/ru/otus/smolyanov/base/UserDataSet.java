package ru.otus.smolyanov.base;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 10 - Hibernate ORM
 */

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "USERDATASET")
public class UserDataSet extends DataSet{

  @Column (name = "name")
  private String name;

  @Column (name = "age")
  private int age;

  @OneToOne (cascade = CascadeType.ALL)
  private AddressDataSet address;

  @OneToMany (cascade = CascadeType.ALL)
  private List<PhoneDataSet> phones;

  public UserDataSet() {
  }

  public UserDataSet(String name, int age, AddressDataSet address, List<PhoneDataSet> phones) {
    this.name = name;
    this.age = age;
    this.address = address;
    this.phones = phones;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public AddressDataSet getAddress() {
    return address;
  }

  public void setAddress(AddressDataSet address) {
    this.address = address;
  }

  public List<PhoneDataSet> getPhones() {
    return phones;
  }

  public void setPhones(List<PhoneDataSet> phones) {
    this.phones = phones;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("UserDataSet{")
        .append("id=")
        .append(getId())
        .append(",name='")
        .append(getName())
        .append("', age=")
        .append(getAge())
        .append(", address=");

    if (getAddress() != null) {
      sb.append(getAddress().toString());
    } else {
      sb.append("{}");
    }

    sb.append(", phones=[");

    if (phones != null) {
      if (!phones.isEmpty()) {
        boolean commaNeed = false;
        for (PhoneDataSet phone : phones) {
          if (commaNeed) {
            sb.append(", ");
          }
          sb.append(phone.toString());
          commaNeed = true;
        }
      }
    }
    sb.append("]");
    sb.append("}");

    return sb.toString();
  }
}
