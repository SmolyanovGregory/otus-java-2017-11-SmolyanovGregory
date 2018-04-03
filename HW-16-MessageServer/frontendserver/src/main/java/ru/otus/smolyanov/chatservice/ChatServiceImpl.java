package ru.otus.smolyanov.chatservice;

import ru.otus.smolyanov.app.Msg;
import ru.otus.smolyanov.base.ChatMessageDataSet;
import ru.otus.smolyanov.channel.SocketMsgWorker;
import ru.otus.smolyanov.messages.SaveChatMessageMsg;
import ru.otus.smolyanov.websocket.ChatWebSocket;
import java.util.Set;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 - Message server
 */

public class ChatServiceImpl implements ChatService {
  private final static Logger logger = LogManager.getLogger(ChatServiceImpl.class.getName());
  private final Set<ChatWebSocket> webSockets;
  private final Gson gson = new Gson();
  private final SocketMsgWorker worker;

  public ChatServiceImpl(SocketMsgWorker worker) {
    this.webSockets = Collections.newSetFromMap(new ConcurrentHashMap<>());
    this.worker = worker;
  }

  @Override
  public void handleMessageRequest(ChatMessageDataSet chatMessage) {
    Msg message = new SaveChatMessageMsg(chatMessage);
    worker.send(message);
    logger.info("Send SaveChatMessage");
  }

  @Override
  public void handleMessageAnswerRequest(ChatMessageDataSet chatMessage) {
    logger.info("Process SaveChatMessageAnswer");
    for (ChatWebSocket ws : webSockets) {
      try {
        ws.sendString(getformattedMessage(chatMessage.getUserName(), chatMessage.getMessageBody()));
      } catch (Exception e) {
        logger.error(e.getMessage());
      }
    }
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
}
