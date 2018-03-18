package ru.otus.smolyanov.servlets;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import ru.otus.smolyanov.cacheservice.CacheService;
import ru.otus.smolyanov.config.AppConfig;
import ru.otus.smolyanov.datasetgenerator.UserDataSetGenerator;
import ru.otus.smolyanov.websocket.AdmWebSocket;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 15 (message system)
 */

@WebServlet(name = "WebSocketAdmServlet", urlPatterns = {"/adm"})
public class WebSocketAdmServlet extends WebSocketServlet{

  private static final String APPLICATION_CONTEXT_PARAM_NAME = "springApplicationContext";
  private static final String CACHE_SERVICE = "cacheService";
  private static final String USER_DATA_SET_GENERATOR = "userDatasetGenerator";
  private ApplicationContext context;

  CacheService cacheService;
  UserDataSetGenerator randomUserGenerator;

  public WebSocketAdmServlet() {
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

    cacheService = (CacheService) context.getBean(CACHE_SERVICE);
    randomUserGenerator = (UserDataSetGenerator) context.getBean(USER_DATA_SET_GENERATOR);

    if (randomUserGenerator.getState().equals(Thread.State.NEW)) {
      randomUserGenerator.start();
    }
  }

  @Override
  public void configure(WebSocketServletFactory factory) {
    factory.setCreator((req, resp) -> new AdmWebSocket(cacheService, randomUserGenerator));
  }
}
