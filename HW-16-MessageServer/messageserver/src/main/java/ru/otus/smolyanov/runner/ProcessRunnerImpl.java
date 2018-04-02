package ru.otus.smolyanov.runner;

import java.io.IOException;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 - Message server
 */

public class ProcessRunnerImpl implements ProcessRunner {
  private Process process;

  @Override
  public void start(String command) throws IOException {
    process = runProcess(command);
  }

  @Override
  public void stop() {

  }

  private Process runProcess(String command) throws IOException {
    ProcessBuilder pb = new ProcessBuilder(command.split(" "));
    //? pb.redirectErrorStream(true);
    Process p = pb.start();

    //StreamListener output = new StreamListener(p.getInputStream(), "OUTPUT");
    //output.start();

    return p;
  }
}
