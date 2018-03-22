package ru.otus.smolyanov.chat;

import ru.otus.smolyanov.app.ChatService;
import ru.otus.smolyanov.app.MessageSystemContext;
import ru.otus.smolyanov.base.ChatMessageDataSet;
import ru.otus.smolyanov.messageSystem.Address;
import ru.otus.smolyanov.messageSystem.Message;
import ru.otus.smolyanov.messageSystem.MessageSystem;
import ru.otus.smolyanov.messages.MsgGetAllChatMessages;
import ru.otus.smolyanov.websocket.ChatWebSocket;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import ru.otus.smolyanov.messages.MsgSaveChatMessage;
import com.google.gson.Gson;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 15 (message system)
 */

public class ChatServiceImpl implements ChatService {

  private final Set<ChatWebSocket> webSockets;
  private final Address address;
  private final MessageSystemContext context;
  private final Gson gson = new Gson();

  public ChatServiceImpl(MessageSystemContext context, Address address) {
    this.context = context;
    this.address = address;
    this.webSockets = Collections.newSetFromMap(new ConcurrentHashMap<>());
  }

  @Override
  public Address getAddress() {
    return address;
  }

  @Override
  public void init() {
    context.getMessageSystem().addAddressee(this);
  }

  @Override
  public void handleMessageRequest(ChatMessageDataSet chatMessage) {
    Message message = new MsgSaveChatMessage(getAddress(), context.getDbAddress(), chatMessage);
    context.getMessageSystem().sendMessage(message);

    for (ChatWebSocket ws : webSockets) {
      try {
        ws.sendString(getformattedMessage(chatMessage.getUserName(), chatMessage.getMessageBody()));
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }

  @Override
  public void handleGetAllMessagesRequest() {
    Message message = new MsgGetAllChatMessages(getAddress(), context.getDbAddress());
    context.getMessageSystem().sendMessage(message);
  }

  @Override
  public MessageSystem getMS() {
    return context.getMessageSystem();
  }

  @Override
  public void add(ChatWebSocket webSocket) {
    webSockets.add(webSocket);
  }

  @Override
  public void remove(ChatWebSocket webSocket) {
    webSockets.remove(webSocket);
  }

  private String getformattedMessage(String user, String chatMessage) {
    Map<String, String> response = new HashMap<>();
    response.put("responseType", "message");
    response.put("body", user + ": " + chatMessage);

    return gson.toJson(response);
  }

  @Override
  public void restoreAllChatMessages (List<ChatMessageDataSet> messageList) {
    StringBuffer sb = new StringBuffer();
    for (ChatMessageDataSet ds : messageList) {
      if (sb.length() == 0) {
        sb.append("\n");
      }
      sb.append(ds.getUserName()).append(": ").append(ds.getMessageBody());
    }

    Map<String, String> response = new HashMap<>();
    response.put("responseType", "history");
    response.put("body", sb.toString());

    for (ChatWebSocket ws : webSockets) {
      try {
        ws.sendString(gson.toJson(response));
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
