package ru.otus.smolyanov.cacheservice;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 (message server)
 */

class Element<V> {
  private final V value;
  private final long creationTime;
  private long lastAccessTime;

  public Element(V value) {
    this.value = value;
    this.creationTime = getCurrentTime();
    this.lastAccessTime = getCurrentTime();
  }

  protected long getCurrentTime() {
    return System.currentTimeMillis();
  }

  protected V getValue() {
    return value;
  }

  protected long getCreationTime() {
    return creationTime;
  }

  protected long getLastAccessTime() {
    return lastAccessTime;
  }

  protected void setAccessed() {
    lastAccessTime = getCurrentTime();
  }
}
