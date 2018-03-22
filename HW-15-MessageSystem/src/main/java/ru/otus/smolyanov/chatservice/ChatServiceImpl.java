package ru.otus.smolyanov.chatservice;

import ru.otus.smolyanov.app.ChatService;
import ru.otus.smolyanov.app.MessageSystemContext;
import ru.otus.smolyanov.base.ChatMessageDataSet;
import ru.otus.smolyanov.messageSystem.Address;
import ru.otus.smolyanov.messageSystem.Message;
import ru.otus.smolyanov.messageSystem.MessageSystem;
import ru.otus.smolyanov.messageSystem.messages.MsgGetAllChatMessages;
import ru.otus.smolyanov.websocket.ChatWebSocket;
import java.util.Set;
import java.util.List;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import ru.otus.smolyanov.messageSystem.messages.MsgSaveChatMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 15 (message system)
 */

public class ChatServiceImpl implements ChatService {

  private final static Logger logger = LogManager.getLogger(ChatServiceImpl.class.getName());
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
  public void handleGetAllMessagesRequest(ChatWebSocket webSocket) {
    Message message = new MsgGetAllChatMessages(getAddress(), context.getDbAddress(), webSocket);
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
    JsonObject jsonObj = new JsonObject();
    jsonObj.addProperty("responseType", "message");
    jsonObj.addProperty("body", user + ": " + chatMessage);

    return gson.toJson(jsonObj);
  }

  @Override
  public void restoreAllChatMessages (List<ChatMessageDataSet> messageList, ChatWebSocket webSocket) {
    StringBuilder sb = new StringBuilder();
    for (ChatMessageDataSet ds : messageList) {
      if (sb.length() != 0) {
        sb.append("\n");
      }
      sb.append(ds.getUserName()).append(": ").append(ds.getMessageBody());
    }

    JsonObject jsonObj = new JsonObject();
    jsonObj.addProperty("responseType", "history");
    jsonObj.addProperty("body", sb.toString());

    try {
      webSocket.sendString(gson.toJson(jsonObj));
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }
}
