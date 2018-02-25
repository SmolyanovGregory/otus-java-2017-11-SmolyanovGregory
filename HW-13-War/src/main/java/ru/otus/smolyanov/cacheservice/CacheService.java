package ru.otus.smolyanov.cacheservice;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 13 - web archive
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
