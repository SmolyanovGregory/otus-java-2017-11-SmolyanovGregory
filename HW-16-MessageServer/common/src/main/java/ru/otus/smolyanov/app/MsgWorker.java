package ru.otus.smolyanov.app;

import ru.otus.smolyanov.channel.Blocks;

import java.io.IOException;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 (message server)
 */

public interface MsgWorker {
  void send(Msg msg);

  Msg pool();

  @Blocks
  Msg take() throws InterruptedException;

  void close() throws IOException;

  Address getProcessingAddress();

  void setProcessingAddress(Address processingAddress);

  int getIdentifier();

  void setIdentifier(int identifier);
}
