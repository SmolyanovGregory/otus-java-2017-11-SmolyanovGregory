package ru.otus.smolyanov.servlets;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.smolyanov.accountservice.AccountService;
import ru.otus.smolyanov.config.AppConfig;
import ru.otus.smolyanov.websocket.SignInWebSocket;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 15 (message system)
 */

@WebServlet(name = "WebSocketSignInServlet", urlPatterns = {"/signin"})
public class WebSocketSignInServlet extends WebSocketServlet {

  private static final String APPLICATION_CONTEXT_PARAM_NAME = "springApplicationContext";
  private static final String ACCOUNT_SERVICE = "accountService";
  private ApplicationContext context;
  private AccountService accountService;

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

    accountService = (AccountService) context.getBean(ACCOUNT_SERVICE);
  }

  @Override
  public void configure(WebSocketServletFactory factory) {
    factory.setCreator((req, resp) -> new SignInWebSocket(accountService));
  }
}
