package ru.otus.smolyanov.cacheservice;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 11 - My cache engine
 */

public interface CacheService<K, V> {

  void put(Element<K, V> element);

  Element<K, V> get(K key);

  int getHitCount();

  int getMissCount();

  void dispose();
}
