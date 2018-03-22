package ru.otus.smolyanov.messages;

import ru.otus.smolyanov.app.ChatService;
import ru.otus.smolyanov.app.MsgToChat;
import ru.otus.smolyanov.base.ChatMessageDataSet;
import ru.otus.smolyanov.messageSystem.Address;
import java.util.List;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 15 (message system)
 */

public class MsgGetAllChatMessagesAnswer extends MsgToChat{

  private final List<ChatMessageDataSet> messageList;

  public MsgGetAllChatMessagesAnswer(Address from, Address to, List<ChatMessageDataSet> messageList) {
    super(from, to);
    this.messageList = messageList;
  }

  @Override
  public void exec(ChatService chatService) {
    chatService.restoreAllChatMessages(messageList);
  }
}
