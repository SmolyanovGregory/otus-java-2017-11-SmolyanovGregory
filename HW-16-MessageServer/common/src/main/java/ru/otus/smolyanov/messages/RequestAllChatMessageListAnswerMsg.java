package ru.otus.smolyanov.messages;

import ru.otus.smolyanov.app.Address;
import ru.otus.smolyanov.app.Msg;
import ru.otus.smolyanov.base.ChatMessageDataSet;
import java.util.List;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 - Message server
 */

public class RequestAllChatMessageListAnswerMsg extends Msg {

  public RequestAllChatMessageListAnswerMsg(List<ChatMessageDataSet> chatMessages) {
    super(RequestAllChatMessageListMsg.class, Address.DATABASE, Address.FRONTEND);
  }

  @Override
  public String toString() {
    return "RequestAllChatMessageListAnswerMsg{}";
  }
}
