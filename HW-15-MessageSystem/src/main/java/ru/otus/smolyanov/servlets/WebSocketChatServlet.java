package ru.otus.smolyanov.servlets;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 15 (message system)
 */

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import org.springframework.context.ApplicationContext;
import ru.otus.smolyanov.app.ChatService;
import ru.otus.smolyanov.websocket.ChatWebSocket;
import static ru.otus.smolyanov.Consts.*;

@WebServlet(name = "WebSocketChatServlet", urlPatterns = {"/chat"})
public class WebSocketChatServlet extends WebSocketServlet{

  private final static int LOGOUT_TIME = 10 * 60 * 1000;
  private ChatService chatService;

  public WebSocketChatServlet() {
  }

  public void init() throws ServletException {
    super.init();
    ServletContext ctx = getServletContext();
    ApplicationContext context;

    Object attribute = ctx.getAttribute(APPLICATION_CONTEXT_PARAM_NAME);
    if (attribute != null) {
      context = (ApplicationContext) attribute;
    } else {
      throw new RuntimeException("Application context not found");
    }

    chatService = (ChatService) context.getBean(CHAT_SERVICE);
  }

  @Override
  public void configure(WebSocketServletFactory factory) {
    factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
    factory.setCreator((req, resp) -> new ChatWebSocket(chatService));
  }

}
