package ru.otus.smolyanov.messages;

import ru.otus.smolyanov.app.Address;
import ru.otus.smolyanov.app.Msg;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 - Message server
 */

public class RequestAllChatMessageListMsg extends Msg {

  public RequestAllChatMessageListMsg() {
    super(RequestAllChatMessageListMsg.class, Address.FRONTEND, Address.DATABASE);
  }

  @Override
  public String toString() {
    return "RequestAllChatMessageListMsg{}";
  }
}
