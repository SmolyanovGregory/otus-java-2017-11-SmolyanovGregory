package ru.otus.smolyanov.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.smolyanov.app.Msg;
import ru.otus.smolyanov.app.Address;
import ru.otus.smolyanov.base.ChatMessageDataSet;
import ru.otus.smolyanov.cacheservice.CacheInfo;
import ru.otus.smolyanov.channel.SocketMsgWorker;
import ru.otus.smolyanov.dbservice.DBService;
import ru.otus.smolyanov.dbservice.DBServiceCachedImpl;
import ru.otus.smolyanov.messages.*;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 - Message server
 */

public class Main {

  private final static Logger logger = LogManager.getLogger(Main.class.getName());
  private final String host;
  private final int port;
  private final int identifier;
  private SocketMsgWorker worker;
  private DBService dbService;
  private final Map<Class, Function<Msg, Msg>> messageHandlers = new HashMap<>();

  public Main(String host, int port, int identifier) {
    this.host = host;
    this.port = port;
    this.identifier = identifier;

    // message handlers
    messageHandlers.put(InitSocketProcessingAddressAnswerMsg.class, processInitSocketProcessingAddressAnswer);
    messageHandlers.put(SaveChatMessage.class, processSaveChatMessage);
    messageHandlers.put(RequestAllChatMessageListMsg.class, processGetAllChatMessages);
  }

  public static void main(String... args) {
    try {
      String host = args[0];
      int port = Integer.valueOf(args[1]);
      int identifier = Integer.valueOf(args[2]);
      logger.info("Started on host="+host+" port="+port);

      new Main(host, port, identifier).run();
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }

  private void run() throws Exception {
    dbService = new DBServiceCachedImpl();
    logger.info("DB service created");

    MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
    CacheInfo cacheInfoMBean = new CacheInfo(dbService.getCache());
    ObjectName mbName = new ObjectName("ru.otus.smolyanov:type=DatabaseServiceCache");
    mbs.registerMBean(cacheInfoMBean, mbName);

    logger.info("Database server started");
    worker = new DatabaseSocketMsgWorker(host, port);
    worker.init();

    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.submit(this::process);

    // initializing server on message server side
    Msg initMsg = new InitSocketProcessingAddressMsg(Address.DATABASE, identifier);
    worker.send(initMsg);

    logger.info("Service initialized message sended");
  }

  @SuppressWarnings("InfiniteLoopStatement")
  private void process() {
    try {
      while (true) {
        Msg msg = this.worker.take();
        logger.info("Message received: " + msg.toString());

        Msg responseMessage = getMessageHandler(msg.getClass()).apply(msg);
        if (responseMessage != null) {
          logger.info("Send response message: "+responseMessage.toString());
          worker.send(responseMessage);
        }
      }
    } catch (InterruptedException e) {
      logger.error(e.getMessage());
    }
  }

  private Function<Msg, Msg> processInitSocketProcessingAddressAnswer = message -> {
    logger.info("Server initialized on message server");
    return null;
  };

  private Function<Msg, Msg> processSaveChatMessage = message -> {
    logger.info("Process SaveChatMessage message...");
    ChatMessageDataSet chatMessage = ((SaveChatMessage) message).getChatMessage();
    dbService.saveChatMessage(chatMessage);
    return null;
  };

  private Function<Msg, Msg> processGetAllChatMessages = message -> {
    logger.info("Process RequestAllChatMessageListMsg message...");
    List<ChatMessageDataSet> chatMessages = dbService.getAllChatMessage();
    return new RequestAllChatMessageListAnswerMsg(chatMessages);
  };

  private Function<Msg, Msg> getMessageHandler(Class klass) {
    return messageHandlers.get(klass);
  }
}
