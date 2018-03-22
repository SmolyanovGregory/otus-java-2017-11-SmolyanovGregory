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
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.smolyanov.app.ChatService;
import ru.otus.smolyanov.config.AppConfig;
import ru.otus.smolyanov.websocket.ChatWebSocket;
import ru.otus.smolyanov.Consts;

@WebServlet(name = "WebSocketChatServlet", urlPatterns = {"/chat"})
public class WebSocketChatServlet extends WebSocketServlet implements Consts {

  private final static int LOGOUT_TIME = 10 * 60 * 1000;
  private ChatService chatService;
  private ApplicationContext context;

  public WebSocketChatServlet() {
  }

  public void init() throws ServletException {
    super.init();
    ServletContext ctx = getServletContext();

    Object attribute = ctx.getAttribute(APPLICATION_CONTEXT_PARAM_NAME);
    if (attribute == null) {
      context = new AnnotationConfigApplicationContext(AppConfig.class);
      ctx.setAttribute(APPLICATION_CONTEXT_PARAM_NAME, context);
    } else {
      context = (ApplicationContext) attribute;
    }

    chatService = (ChatService) context.getBean(CHAT_SERVICE);
  }

  @Override
  public void configure(WebSocketServletFactory factory) {
    factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
    factory.setCreator((req, resp) -> new ChatWebSocket(chatService));
  }

}
