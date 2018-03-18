package ru.otus.smolyanov.cacheservice;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 15 (message system)
 */

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;
import java.lang.ref.SoftReference;

public class CacheServiceImpl<V> implements CacheService<V> {

  private static final int TIME_THRESHOLD_MS = 5;

  public static final String MAX_SIZE = "max_size";
  public static final String lIFE_TIME_MS = "life_time_ms";
  public static final String IDLE_TIME_MS = "idle_time_ms";
  public static final String IS_ETERNAL = "is_eternal";

  private final int maxElementsCount;
  private final long lifeTimeMs;
  private final long idleTimeMs;
  private final boolean isEternal;

  private int hit = 0;
  private int miss = 0;

  private final Map<ElementKey, SoftReference<Element<V>>> elements = new LinkedHashMap<>();
  private final Timer timer = new Timer();

  public static class Builder<V> {
    private int maxElementsCount;
    private long lifeTimeMs;
    private long idleTimeMs;
    private boolean isEternal = true;

    public Builder(int maxElementsCount) {
      this.maxElementsCount = maxElementsCount;
    }

    public Builder lifeTimeMs(int val) {
      lifeTimeMs = val;
      return this;
    }

    public Builder idleTimeMs(int val) {
      idleTimeMs = val;
      return this;
    }

    public Builder isEternal(boolean val) {
      isEternal = val;
      return this;
    }

    public CacheServiceImpl<V> build() {
      return new CacheServiceImpl<>(this);
    }
  }

  private CacheServiceImpl(Builder builder) {
    maxElementsCount = builder.maxElementsCount;
    lifeTimeMs = builder.lifeTimeMs > 0 ? builder.lifeTimeMs : 0;
    idleTimeMs = builder.idleTimeMs > 0 ? builder.idleTimeMs : 0;
    isEternal = builder.lifeTimeMs == 0 && builder.idleTimeMs == 0 || builder.isEternal;
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
  public void put(ElementKey key, V value) {
    if (maxElementsCount > 0) {

      if (elements.size() == maxElementsCount) {
        ElementKey firstKey = elements.keySet().iterator().next();
        elements.remove(firstKey);
        miss++;
      }

      Element element = new Element<>(value);
      elements.put(key, new SoftReference(element));

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
  }

  @Override
  public V get(ElementKey key) {
    if (maxElementsCount > 0) {
      SoftReference<Element<V>> softReference = elements.get(key);

      if (softReference != null) {
        hit++;
        if (softReference.get() != null) {
          softReference.get().setAccessed();
          return softReference.get().getValue();
        }
        else {
          return null;
        }
      } else {
        miss++;
        return null;
      }
    } else {
      return null;
    }
  }

  private TimerTask getTimerTask(final ElementKey key, Function<Element<V>, Long> timeFunction) {
    return new TimerTask() {
      @Override
      public void run() {
        Element<V> element = elements.get(key).get();
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

  @Override
  public void dispose() {
    timer.cancel();
  }

  @Override
  public int getSize() {
    return maxElementsCount;
  }

  @Override
  public long getLifeTimeMs() {
    return lifeTimeMs;
  }

  @Override
  public long getIdleTimeMs() {
    return idleTimeMs;
  }

  @Override
  public boolean getIsEternal() {
    return isEternal;
  }
}
