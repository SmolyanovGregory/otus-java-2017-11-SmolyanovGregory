package ru.otus.smolyanov.runner;

import java.io.IOException;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 - Message server
 */

public interface ProcessRunner {

  void start(String command) throws IOException;

  void stop();
}
