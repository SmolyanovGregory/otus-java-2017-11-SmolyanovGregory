package ru.otus.smolyanov.messageSystem.messages;

import ru.otus.smolyanov.app.DBService;
import ru.otus.smolyanov.app.MsgToDB;
import ru.otus.smolyanov.base.ChatMessageDataSet;
import ru.otus.smolyanov.messageSystem.Address;
import ru.otus.smolyanov.websocket.ChatWebSocket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 15 (message system)
 */

public class MsgGetAllChatMessages extends MsgToDB {
  private final static Logger logger = LogManager.getLogger(MsgGetAllChatMessages.class.getName());
  private final ChatWebSocket webSocket;
  public MsgGetAllChatMessages(Address from, Address to, ChatWebSocket webSocket) {
    super(from, to);
    this.webSocket = webSocket;
  }

  @Override
  public void exec(DBService dbService) {
    logger.info("MsgGetAllChatMessages exec...");
    List<ChatMessageDataSet> allChatMessages = dbService.getAllChatMessage();
    dbService.getMS().sendMessage(new MsgGetAllChatMessagesAnswer(getTo(), getFrom(), allChatMessages, webSocket));
  }
}
