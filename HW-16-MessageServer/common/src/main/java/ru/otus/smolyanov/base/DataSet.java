package ru.otus.smolyanov.base;

import javax.persistence.*;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 (message server)
 */

@MappedSuperclass
public abstract class DataSet {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
