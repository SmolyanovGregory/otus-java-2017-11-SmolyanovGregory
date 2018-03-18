package ru.otus.smolyanov.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.otus.smolyanov.accountservice.AccountService;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 15 (message system)
 */

@SuppressWarnings("UnusedDeclaration")
@WebSocket
public class SignInWebSocket {

  private Session session;
  private AccountService accountService;
  private final Gson gson = new Gson();

  public SignInWebSocket(AccountService accountService) {
    this.accountService = accountService;
  }

  @OnWebSocketConnect
  public void onOpen(Session session) {
    this.session = session;
  }

  @OnWebSocketMessage
  public void onMessage(String data) {
    JsonObject jsonObject = gson.fromJson(data, JsonObject.class);
    String login = jsonObject.get("login").getAsString();
    String password = jsonObject.get("password").getAsString();

    String response = "false";
    if (accountService.isAdmin(login, password)) {
      response = "true";
    }
    sendString(response);
  }

  @OnWebSocketClose
  public void onClose(int statusCode, String reason) {
  }

  public void sendString(String data) {
    try {
      session.getRemote().sendString(data);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
