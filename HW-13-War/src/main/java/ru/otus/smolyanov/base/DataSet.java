package ru.otus.smolyanov.base;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 13 - web archive
 */

import javax.persistence.*;

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
