package ru.otus.smolyanov.messages;

import ru.otus.smolyanov.app.Address;
import ru.otus.smolyanov.app.Msg;
import ru.otus.smolyanov.base.ChatMessageDataSet;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 (message server)
 */

public class SaveChatMessageMsg extends Msg {

  private final ChatMessageDataSet chatMessage;

  public SaveChatMessageMsg(ChatMessageDataSet chatMessage) {
    super(SaveChatMessageMsg.class, Address.FRONTEND, Address.DATABASE);
    this.chatMessage = chatMessage;
  }

  public ChatMessageDataSet getChatMessage() {
    return chatMessage;
  }

  @Override
  public String toString() {
    return "SaveChatMessageMsg{" + "user=" + chatMessage.getUserName()+ ", messageBody="+chatMessage.getMessageBody() +'}';
  }

}
