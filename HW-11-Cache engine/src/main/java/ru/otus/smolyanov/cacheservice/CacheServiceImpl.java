package ru.otus.smolyanov.cacheservice;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 11 - My cache engine
 */

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;
import java.lang.ref.SoftReference;

public class CacheServiceImpl<K, V> implements CacheService<K, V> {

  private static final int TIME_THRESHOLD_MS = 5;

  private final int maxElementsCount;
  private final long lifeTimeMs;
  private final long idleTimeMs;
  private final boolean isEternal;

  private int hit = 0;
  private int miss = 0;

  private final Map<K, SoftReference<Element<K, V>>> elements = new LinkedHashMap<>();
  private final Timer timer = new Timer();

  public CacheServiceImpl(int maxElementsCount, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
    this.maxElementsCount = maxElementsCount;
    this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
    this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
    this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0 || isEternal;
  }

  @Override
  public void put(Element<K, V> element) {
    if (elements.size() == maxElementsCount) {
      K firstKey = elements.keySet().iterator().next();
      elements.remove(firstKey);
      miss++;
    }

    K key = element.getKey();
    elements.put(key, new SoftReference<>(element));

    if (!isEternal) {
      if (lifeTimeMs != 0) {
        TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
        timer.schedule(lifeTimerTask, lifeTimeMs);
      }
      if (idleTimeMs != 0) {
        TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
        timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
      }
    }
  }

  @Override
  public Element<K, V> get(K key) {
    SoftReference<Element<K, V>> softReference = elements.get(key);

    if (softReference != null) {
      hit++;
      if (softReference.get() != null) {
        softReference.get().setAccessed();
      }
      return softReference.get();
    } else {
      miss++;
      return null;
    }
  }

  @Override
  public int getHitCount() {
    return hit;
  }

  @Override
  public int getMissCount() {
    return miss;
  }

  @Override
  public void dispose() {
    timer.cancel();
  }

  private TimerTask getTimerTask(final K key, Function<Element<K, V>, Long> timeFunction) {
    return new TimerTask() {
      @Override
      public void run() {
        Element<K, V> element = elements.get(key).get();
        if (element == null || isT1BeforeT2(timeFunction.apply(element), System.currentTimeMillis())) {
          elements.remove(key);
          this.cancel();
        }
      }
    };
  }

  private boolean isT1BeforeT2(long t1, long t2) {
    return t1 < t2 + TIME_THRESHOLD_MS;
  }
}
