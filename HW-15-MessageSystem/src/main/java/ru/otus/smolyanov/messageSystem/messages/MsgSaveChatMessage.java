package ru.otus.smolyanov.messageSystem.messages;

import ru.otus.smolyanov.app.DBService;
import ru.otus.smolyanov.app.MsgToDB;
import ru.otus.smolyanov.base.ChatMessageDataSet;
import ru.otus.smolyanov.messageSystem.Address;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 15 (message system)
 */

public class MsgSaveChatMessage extends MsgToDB {
  private final static Logger logger = LogManager.getLogger(MsgSaveChatMessage.class.getName());
  private final ChatMessageDataSet chatMessage;

  public MsgSaveChatMessage(Address from, Address to, ChatMessageDataSet chatMessage) {
    super(from, to);
    this.chatMessage = chatMessage;
  }

  @Override
  public void exec(DBService dbService) {
    logger.info("MsgSaveChatMessage exec...");
    dbService.saveChatMessage(chatMessage);
  }
}
