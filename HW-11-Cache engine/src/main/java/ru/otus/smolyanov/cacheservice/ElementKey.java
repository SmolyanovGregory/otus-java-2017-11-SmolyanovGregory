package ru.otus.smolyanov.cacheservice;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 11 - My cache engine
 */

import java.util.Objects;

public class ElementKey {

  private final long id;
  private final Class klass;

  public ElementKey(long id, Class klass) {
    this.id = id;
    this.klass = klass;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, klass);
  }

  @Override
  public boolean equals(Object o) {
    if (o != this) {
      if (o != null && this.getClass() == o.getClass()) {
        ElementKey anotherKey = (ElementKey) o;
        return this.id == anotherKey.id && this.klass.equals(anotherKey.klass);

      } else
        return false;
    } else
      return true;
  }
}
