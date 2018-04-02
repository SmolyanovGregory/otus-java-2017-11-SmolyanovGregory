package ru.otus.smolyanov.servlets;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import javax.servlet.annotation.WebServlet;
import ru.otus.smolyanov.chatservice.ChatService;
import ru.otus.smolyanov.websocket.ChatWebSocket;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 - Message server
 */

@WebServlet(name = "WebSocketChatServlet", urlPatterns = {"/chat"})
public class WebSocketChatServlet extends WebSocketServlet {

  private final static int LOGOUT_TIME = 10 * 60 * 1000;
  private final ChatService chatService;

  public WebSocketChatServlet(ChatService chatService) {
    this.chatService = chatService;
  }

  @Override
  public void configure(WebSocketServletFactory factory) {
    factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
    factory.setCreator((req, resp) -> new ChatWebSocket(chatService));
  }

}
