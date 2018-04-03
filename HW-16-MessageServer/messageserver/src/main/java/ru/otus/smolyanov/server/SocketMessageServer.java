package ru.otus.smolyanov.server;

import ru.otus.smolyanov.app.Msg;
import ru.otus.smolyanov.app.Address;
import ru.otus.smolyanov.channel.Blocks;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.smolyanov.app.MsgWorker;
import ru.otus.smolyanov.channel.SocketMsgWorker;
import ru.otus.smolyanov.messages.*;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 - Message server
 */

public class SocketMessageServer implements SocketMessageServerMBean {
  private static final Logger logger = LogManager.getLogger(SocketMessageServer.class.getName());
  private static final int THREADS_NUMBER = 1;
  private final int port;
  private final ExecutorService executor;
  private final List<MsgWorker> workers;

  private static final int DELAY_MS = 100;

  public SocketMessageServer(int port) {
    this.port = port;
    executor = Executors.newFixedThreadPool(THREADS_NUMBER);
    workers = new CopyOnWriteArrayList<>();
  }

  @Blocks
  public void start() throws Exception {
    executor.submit(this::process);

    try (ServerSocket serverSocket = new ServerSocket(port)) {
      logger.info("Server started on port: " + serverSocket.getLocalPort());

      while (!executor.isShutdown()) {
        Socket socket = serverSocket.accept(); //blocks
        logger.info("Socket - ok");
        SocketMsgWorker worker = new SocketMsgWorker(socket);
        worker.init();
        workers.add(worker);
      }
    }
  }

  @SuppressWarnings("InfiniteLoopStatement")
  private void process() {
    while (true) {
      for (MsgWorker worker : workers) {

        Msg msg = worker.pool();
        while (msg != null) {
          logger.info("Message received: " + msg.toString());

          try {
            if (msg instanceof InitSocketProcessingAddressMsg) {
              // init worker processing address - db server or frontend server
              InitSocketProcessingAddressMsg initMsg = (InitSocketProcessingAddressMsg) msg;
              worker.setProcessingAddress(initMsg.getProcessingAddress());
              worker.setIdentifier(initMsg.getIdentifier());
              // send response
              worker.send(new InitSocketProcessingAddressAnswerMsg());
            } else {

              // send message to selected recipients
              List<MsgWorker> addressees = getAddresseeListForMsg(msg.getTo());
              for (MsgWorker addressee : addressees) {
                addressee.send(msg);
              }
            }

            msg = worker.pool();
          } catch (Exception e) {
            logger.error(e.getMessage());
          }
        }
      }

      try {
        Thread.sleep(DELAY_MS);
      } catch (InterruptedException e) {
        logger.error(e.toString());
      }
    }
  }

  @Override
  public boolean getRunning() {
    return true;
  }

  @Override
  public void setRunning(boolean running) {
    if (!running) {
      executor.shutdown();
      logger.info("Bye.");
    }
  }

  private List<MsgWorker> getAddresseeListForMsg(Address address) {
    List<MsgWorker> result = new ArrayList<>();

    if (address == Address.FRONTEND) {
      // to all FRONTEND
      result = workers.stream().filter((c)-> c.getProcessingAddress() == Address.FRONTEND).collect(Collectors.toList());
    } else if (address == Address.DATABASE) {
      // select one DATABASE worker
      List<MsgWorker> filteredWorkers = workers.stream().filter((c)-> c.getProcessingAddress() == Address.DATABASE).collect(Collectors.toList());
      int workerIdx = (int) (Math.random() * filteredWorkers.size());
      MsgWorker selectedWorker = filteredWorkers.get(workerIdx);
      logger.info("Balanced to DB worker "+selectedWorker.getIdentifier());
      result.add(selectedWorker);
    }

    return result;
  }
}
