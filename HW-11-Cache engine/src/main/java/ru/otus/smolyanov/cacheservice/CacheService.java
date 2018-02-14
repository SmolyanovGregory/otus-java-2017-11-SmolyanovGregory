package ru.otus.smolyanov.cacheservice;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 11 - My cache engine
 */

public interface CacheService<V> {

  void put(ElementKey key, V value);

  V get(ElementKey key);

  int getHitCount();

  int getMissCount();

  void dispose();
}
