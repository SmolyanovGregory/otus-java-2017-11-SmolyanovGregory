package ru.otus.smolyanov.cacheservice;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 15 (message system)
 */

public interface CacheService<V> {

  void put(ElementKey key, V value);

  V get(ElementKey key);

  int getHitCount();

  int getMissCount();

  void dispose();

  int getSize();

  long getLifeTimeMs();

  long getIdleTimeMs();

  boolean getIsEternal();
}
