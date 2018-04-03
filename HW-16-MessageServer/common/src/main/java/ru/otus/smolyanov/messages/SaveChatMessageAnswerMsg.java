package ru.otus.smolyanov.messages;

import ru.otus.smolyanov.app.Address;
import ru.otus.smolyanov.app.Msg;
import ru.otus.smolyanov.base.ChatMessageDataSet;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 (message server)
 */

public class SaveChatMessageAnswerMsg extends Msg {

  private final ChatMessageDataSet chatMessage;

  public SaveChatMessageAnswerMsg(ChatMessageDataSet chatMessage) {
    super(SaveChatMessageAnswerMsg.class, Address.DATABASE, Address.FRONTEND);
    this.chatMessage = chatMessage;
  }

  public ChatMessageDataSet getChatMessage() {
    return chatMessage;
  }

  @Override
  public String toString() {
    return "SaveChatMessageAnswerMsg{" + "user=" + chatMessage.getUserName()+ ", messageBody="+chatMessage.getMessageBody() +'}';
  }
}
