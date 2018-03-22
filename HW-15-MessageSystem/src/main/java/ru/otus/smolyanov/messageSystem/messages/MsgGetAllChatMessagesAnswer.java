package ru.otus.smolyanov.messageSystem.messages;

import ru.otus.smolyanov.app.ChatService;
import ru.otus.smolyanov.app.MsgToChat;
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

public class MsgGetAllChatMessagesAnswer extends MsgToChat{
  private final static Logger logger = LogManager.getLogger(MsgGetAllChatMessagesAnswer.class.getName());
  private final List<ChatMessageDataSet> messageList;
  private final ChatWebSocket webSocket;

  public MsgGetAllChatMessagesAnswer(Address from, Address to, List<ChatMessageDataSet> messageList, ChatWebSocket webSocket) {
    super(from, to);
    this.messageList = messageList;
    this.webSocket = webSocket;
  }

  @Override
  public void exec(ChatService chatService) {
    logger.info("MsgGetAllChatMessagesAnswer exec...");
    chatService.restoreAllChatMessages(messageList, webSocket);
  }
}
