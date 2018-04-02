package ru.otus.smolyanov.main;

import ru.otus.smolyanov.channel.SocketMsgWorker;
import java.io.IOException;
import java.net.Socket;
/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 - Message server
 */

public class DatabaseSocketMsgWorker extends SocketMsgWorker {

  private final Socket socket;

  DatabaseSocketMsgWorker(String host, int port) throws IOException {
    this(new Socket(host, port));
  }

  private DatabaseSocketMsgWorker(Socket socket) throws IOException {
    super(socket);
    this.socket = socket;
  }

  public void close() throws IOException {
    super.close();
    socket.close();
  }
}
