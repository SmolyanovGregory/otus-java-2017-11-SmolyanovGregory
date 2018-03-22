package ru.otus.smolyanov.messages;

import ru.otus.smolyanov.app.DBService;
import ru.otus.smolyanov.app.MsgToDB;
import ru.otus.smolyanov.base.ChatMessageDataSet;
import ru.otus.smolyanov.messageSystem.Address;
import java.util.List;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 15 (message system)
 */

public class MsgGetAllChatMessages extends MsgToDB {

  public MsgGetAllChatMessages(Address from, Address to) {
    super(from, to);
  }

  @Override
  public void exec(DBService dbService) {
    List<ChatMessageDataSet> allChatMessages = dbService.getAllChatMessage();
    dbService.getMS().sendMessage(new MsgGetAllChatMessagesAnswer(getTo(), getFrom(), allChatMessages));
  }
}
