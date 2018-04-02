package ru.otus.smolyanov.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.smolyanov.app.Msg;
import ru.otus.smolyanov.app.Address;
import ru.otus.smolyanov.channel.SocketMsgWorker;
import ru.otus.smolyanov.chatservice.ChatService;
import ru.otus.smolyanov.chatservice.ChatServiceImpl;
import ru.otus.smolyanov.messages.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.smolyanov.servlets.WebSocketChatServlet;

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
  private ChatService chatService;
  private static final String PUBLIC_HTML = "public_html";

  public Main(String host, int port, int identifier) {
    this.host = host;
    this.port = port;
    this.identifier = identifier;
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
    logger.info("Frontend server started");
    worker = new FrontendSocketMsgWorker(host, port);
    /*
    chatService = new ChatServiceImpl();

    ResourceHandler resourceHandler = new ResourceHandler();
    resourceHandler.setResourceBase(PUBLIC_HTML);

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

    context.addServlet(new ServletHolder(new WebSocketChatServlet(chatService)), "/chat");

    int serverPort = port+identifier;
    Server server = new Server(serverPort);

    server.setHandler(new HandlerList(resourceHandler, context));

    server.start();
    logger.info("Jetty started on port "+serverPort);
*/
    worker.init();

    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.submit(this::process);

    // initializing server on message server
    Msg initMsg = new InitSocketProcessingAddressMsg(Address.FRONTEND, identifier);
    worker.send(initMsg);

    //server.join();
  }

  @SuppressWarnings("InfiniteLoopStatement")
  private void process() {
    try {
      while (true) {
        Msg msg = this.worker.take();
        logger.info("Message received: " + msg.toString());

        if (msg instanceof InitSocketProcessingAddressAnswerMsg) {
          logger.info("Server initialized on message server");
        }

//        if (isServerInitializedOnMessageServer) {
//          logger.info("process message "+msg.toString());
//        }

      }
    } catch (InterruptedException e) {
      logger.error(e.getMessage());
    }
  }
}
