package ru.otus.smolyanov.messages;

import ru.otus.smolyanov.app.Address;
import ru.otus.smolyanov.app.Msg;
import ru.otus.smolyanov.base.ChatMessageDataSet;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 (message server)
 */

public class SaveChatMessage extends Msg {

  private final ChatMessageDataSet chatMessage;

  public SaveChatMessage(ChatMessageDataSet chatMessage) {
    super(SaveChatMessage.class, Address.FRONTEND, Address.DATABASE);
    this.chatMessage = chatMessage;
  }

  public ChatMessageDataSet getChatMessage() {
    return chatMessage;
  }

  @Override
  public String toString() {
    return "SaveChatMessage{" + "user=" + chatMessage.getUserName()+ ", messageBody="+chatMessage.getMessageBody() +'}';
  }

}
