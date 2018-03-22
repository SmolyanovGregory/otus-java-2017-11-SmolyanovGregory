package ru.otus.smolyanov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import ru.otus.smolyanov.app.ChatService;
import ru.otus.smolyanov.chatservice.ChatServiceImpl;
import ru.otus.smolyanov.app.DBService;
import ru.otus.smolyanov.dbservice.DBServiceCachedImpl;
import ru.otus.smolyanov.messageSystem.Address;
import ru.otus.smolyanov.messageSystem.MessageSystem;
import ru.otus.smolyanov.app.MessageSystemContext;
import ru.otus.smolyanov.Consts;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 15 (message system)
 */

@Configuration
public class AppConfig implements Consts{

  @Bean(name = MESSAGE_SYSTEM)
  @Description("Provides a message system")
  public MessageSystem messageSystem() {
    return new MessageSystem();
  }

  @Bean(name = MESSAGE_SYSTEM_CONTEXT)
  @Description("Provides a message system context")
  public MessageSystemContext messageSystemContext() {
    MessageSystemContext context = new MessageSystemContext(messageSystem());
    context.setDbAddress(new Address("DB"));
    context.setChatAddress(new Address("Chat"));

    return context;
  }

  @Bean(name = CHAT_SERVICE)
  @Description("Provides a chat service")
  public ChatService chatService() {
    MessageSystemContext context = messageSystemContext();
    return new ChatServiceImpl(context, context.getChatAddress());
  }

  @Bean(name = DB_SERVICE)
  @Description("Provides a database service")
  public DBService dbService() {
    MessageSystemContext context = messageSystemContext();
    return new DBServiceCachedImpl(context, context.getDbAddress());
  }
}
