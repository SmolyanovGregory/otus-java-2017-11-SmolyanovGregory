package ru.otus.smolyanov.channel;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.otus.smolyanov.app.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 - Message server
 */

public class SocketMsgWorker implements MsgWorker {
  private static final Logger logger = LogManager.getLogger(SocketMsgWorker.class.getName());
  private static final int WORKERS_COUNT = 2;

  private final BlockingQueue<Msg> output = new LinkedBlockingQueue<>();
  private final BlockingQueue<Msg> input = new LinkedBlockingQueue<>();

  private final ExecutorService executor;
  private final Socket socket;
  private Address processingAddress;
  private int identifier;

  public SocketMsgWorker(Socket socket) {
    this.socket = socket;
    this.executor = Executors.newFixedThreadPool(WORKERS_COUNT);
    this.processingAddress = Address.UNDEFINED;
  }

  @Override
  public void send(Msg msg) {
    output.add(msg);
  }

  @Override
  public Msg pool() {
    return input.poll();
  }

  @Override
  public Msg take() throws InterruptedException {
    return input.take();
  }

  @Override
  public void close() throws IOException {
    executor.shutdown();
  }

  public void init() {
    executor.execute(this::sendMessage);
    executor.execute(this::receiveMessage);
  }

  @Blocks
  private void sendMessage() {
    try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
      while (socket.isConnected()) {
        Msg msg = output.take(); //blocks
        String json = new Gson().toJson(msg);

        out.println(json);
        out.println();//line with json + an empty line
      }
    } catch (InterruptedException | IOException e) {
      logger.error(e.getMessage());
    }
  }

  @Blocks
  private void receiveMessage() {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
      String inputLine;
      StringBuilder stringBuilder = new StringBuilder();
      while ((inputLine = in.readLine()) != null) { //blocks
        //System.out.println("Message received: " + inputLine);
        stringBuilder.append(inputLine);
        if (inputLine.isEmpty()) { //empty line is the end of the message
          String json = stringBuilder.toString();
          Msg msg = getMsgFromJSON(json);
          input.add(msg);
          stringBuilder = new StringBuilder();
        }
      }
    } catch (IOException | ParseException e) {
      logger.error(e.getMessage());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static Msg getMsgFromJSON(String json) throws ParseException, ClassNotFoundException {
    JSONParser jsonParser = new JSONParser();
    JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
    String className = (String) jsonObject.get(Msg.CLASS_NAME_VARIABLE);
    Class<?> msgClass = Class.forName(className);
    return (Msg) new Gson().fromJson(json, msgClass);
  }

  @Override
  public Address getProcessingAddress() {
    return processingAddress;
  }

  @Override
  public void setProcessingAddress(Address processingAddress) {
    this.processingAddress = processingAddress;
  }

  @Override
  public int getIdentifier() {
    return identifier;
  }

  @Override
  public void setIdentifier(int identifier) {
    this.identifier = identifier;
  }
}
