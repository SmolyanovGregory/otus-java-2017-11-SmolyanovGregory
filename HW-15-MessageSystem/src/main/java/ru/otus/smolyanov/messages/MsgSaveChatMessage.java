package ru.otus.smolyanov.messages;

import ru.otus.smolyanov.app.DBService;
import ru.otus.smolyanov.app.MsgToDB;
import ru.otus.smolyanov.base.ChatMessageDataSet;
import ru.otus.smolyanov.messageSystem.Address;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 15 (message system)
 */

public class MsgSaveChatMessage extends MsgToDB {
  private final ChatMessageDataSet chatMessage;

  public MsgSaveChatMessage(Address from, Address to, ChatMessageDataSet chatMessage) {
    super(from, to);
    this.chatMessage = chatMessage;
  }

  @Override
  public void exec(DBService dbService) {
    dbService.saveChatMessage(chatMessage);
  }
}
