package ru.otus.smolyanov.servlets.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.smolyanov.Consts;
import ru.otus.smolyanov.app.ChatService;
import ru.otus.smolyanov.config.AppConfig;
import ru.otus.smolyanov.app.DBService;
import ru.otus.smolyanov.messageSystem.MessageSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppServletContextListener implements ServletContextListener, Consts {

  private final static Logger logger = LogManager.getLogger(AppServletContextListener.class.getName());
  private ApplicationContext context;

  @Override
  public void contextInitialized(ServletContextEvent event) {
    logger.info("Initializing context...");
    context = new AnnotationConfigApplicationContext(AppConfig.class);

    ChatService chatService = (ChatService) context.getBean(CHAT_SERVICE);
    DBService dbService = (DBService) context.getBean(DB_SERVICE);
    MessageSystem messageSystem = (MessageSystem) context.getBean(MESSAGE_SYSTEM);

    chatService.init();
    dbService.init();
    messageSystem.start();

    event.getServletContext().setAttribute(APPLICATION_CONTEXT_PARAM_NAME, context);
  }

  @Override
  public void contextDestroyed(ServletContextEvent event) {
    MessageSystem messageSystem = (MessageSystem) context.getBean(MESSAGE_SYSTEM);
    messageSystem.dispose();
  }
}
