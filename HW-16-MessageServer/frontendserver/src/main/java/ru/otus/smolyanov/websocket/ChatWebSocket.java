package ru.otus.smolyanov.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.otus.smolyanov.chatservice.ChatService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import ru.otus.smolyanov.base.ChatMessageDataSet;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 - Message server
 */

@SuppressWarnings("UnusedDeclaration")
@WebSocket
public class ChatWebSocket {

  private final ChatService chatService;
  private Session session;
  private final Gson gson;

  public ChatWebSocket(ChatService chatService) {
    this.chatService = chatService;
    this.gson = new Gson();
  }

  @OnWebSocketConnect
  public void onOpen(Session session) {
    chatService.add(this);
    this.session = session;
  }

  @OnWebSocketMessage
  public void onMessage(String data) {
    JsonObject jsonObject = gson.fromJson(data, JsonObject.class);
    String msgType = jsonObject.get("msgType").getAsString();

    switch (msgType) {
      case "message" :
        String user = jsonObject.get("user").getAsString();
        String message = jsonObject.get("message").getAsString();
        chatService.handleMessageRequest(new ChatMessageDataSet(user, message));
        break;

      case "history" :
        chatService.handleGetAllMessagesRequest(this);
        break;
    }
  }

  @OnWebSocketClose
  public void onClose(int statusCode, String reason) {
    chatService.remove(this);
  }

  public void sendString(String data) {
    try {
      session.getRemote().sendString(data);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}