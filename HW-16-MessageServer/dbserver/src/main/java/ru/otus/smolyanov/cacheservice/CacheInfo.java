package ru.otus.smolyanov.cacheservice;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 (message server)
 */

public class CacheInfo implements CacheInfoMBean {

  private final CacheService cacheService;

  public CacheInfo(CacheService cacheService) {
    this.cacheService = cacheService;
  }

  @Override
  public int getHitCount() {
    return cacheService.getHitCount();
  }

  @Override
  public int getMissCount() {
    return cacheService.getMissCount();
  }

  @Override
  public int getSize() {
    return cacheService.getSize();
  }

  @Override
  public long getLifeTimeMs() {
    return cacheService.getLifeTimeMs();
  }

  @Override
  public long getIdleTimeMs() {
    return cacheService.getIdleTimeMs();
  }

  @Override
  public boolean getIsEternal() {
    return cacheService.getIsEternal();
  }
}
