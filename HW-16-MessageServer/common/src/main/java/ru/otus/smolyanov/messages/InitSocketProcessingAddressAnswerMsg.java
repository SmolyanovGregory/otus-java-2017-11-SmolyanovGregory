package ru.otus.smolyanov.messages;

import ru.otus.smolyanov.app.Msg;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 - Message server
 */

public class InitSocketProcessingAddressAnswerMsg extends Msg {

  public InitSocketProcessingAddressAnswerMsg() {
    super(InitSocketProcessingAddressAnswerMsg.class);
  }

  @Override
  public String toString() {
    return "InitWorkerProcessingAddressAnswerMsg{}";
  }
}
