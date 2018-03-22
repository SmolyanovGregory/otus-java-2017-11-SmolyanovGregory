package ru.otus.smolyanov.app;

import ru.otus.smolyanov.base.ChatMessageDataSet;
import ru.otus.smolyanov.messageSystem.Addressee;
import ru.otus.smolyanov.websocket.ChatWebSocket;
import java.util.List;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 15 (message system)
 */

public interface ChatService extends Addressee {

  void init();

  void add(ChatWebSocket webSocket);

  void remove(ChatWebSocket webSocket);

  void handleMessageRequest(ChatMessageDataSet chatMessage);

  void restoreAllChatMessages(List<ChatMessageDataSet> messageList, ChatWebSocket webSocket);

  void handleGetAllMessagesRequest(ChatWebSocket webSocket);
}