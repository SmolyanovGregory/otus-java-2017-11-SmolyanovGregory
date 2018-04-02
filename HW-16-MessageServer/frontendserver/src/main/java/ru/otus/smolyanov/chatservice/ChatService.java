package ru.otus.smolyanov.chatservice;

import ru.otus.smolyanov.base.ChatMessageDataSet;
import ru.otus.smolyanov.websocket.ChatWebSocket;
import java.util.List;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 - Message server
 */

public interface ChatService {

  void add(ChatWebSocket webSocket);

  void remove(ChatWebSocket webSocket);

  void handleMessageRequest(ChatMessageDataSet chatMessage);

  void restoreAllChatMessages(List<ChatMessageDataSet> messageList, ChatWebSocket webSocket);

  void handleGetAllMessagesRequest(ChatWebSocket webSocket);
}