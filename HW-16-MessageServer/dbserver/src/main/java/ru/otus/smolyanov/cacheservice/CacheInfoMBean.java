package ru.otus.smolyanov.cacheservice;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 (message server)
 */

public interface CacheInfoMBean {
  int getHitCount();

  int getMissCount();

  int getSize();

  long getLifeTimeMs();

  long getIdleTimeMs();

  boolean getIsEternal();
}
