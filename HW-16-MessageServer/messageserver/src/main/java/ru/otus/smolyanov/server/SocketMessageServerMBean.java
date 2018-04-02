package ru.otus.smolyanov.server;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 - Message server
 */

public interface SocketMessageServerMBean {
  boolean getRunning();

  void setRunning(boolean running);
}
