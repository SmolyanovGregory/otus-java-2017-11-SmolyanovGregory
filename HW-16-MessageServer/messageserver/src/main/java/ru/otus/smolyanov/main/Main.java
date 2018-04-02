package ru.otus.smolyanov.main;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 - Message server
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import ru.otus.smolyanov.runner.ProcessRunnerImpl;
import ru.otus.smolyanov.server.SocketMessageServer;
import ru.otus.smolyanov.util.PropertiesHelper;

public class Main {

  private static final Logger logger = LogManager.getLogger(Main.class.getName());
  private static final String APP_PROPERTIES_NAME = "app.properties";
  private static final String DBSERVER_PROPERTIES_NAME = "dbserver.properties";
  private static final String FRONTENDSERVER_PROPERTIES_NAME = "frontendserver.properties";
  private static final int DB_SERVER_START_DELAY = 1;
  private static final int FRONTEND_SERVER_START_DELAY = 5;

  public static void main(String ... args) {
    try {
      new Main().run();
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }

  private void run() throws Exception {
    Properties appProperties = PropertiesHelper.getProperies(APP_PROPERTIES_NAME);
    String host = appProperties.getProperty("host");
    int port = Integer.valueOf(appProperties.getProperty("port"));

    MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
    ObjectName name = new ObjectName("ru.otus.smolyanov:type=MessageServer");
    SocketMessageServer server = new SocketMessageServer(port);
    mbs.registerMBean(server, name);

    logger.info("Message server started");

    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    startDbServers(executorService, host, port, DB_SERVER_START_DELAY);
    startFrontendServers(executorService, host, port, FRONTEND_SERVER_START_DELAY);

    server.start();

    executorService.shutdown();
  }

  private void startClient(ScheduledExecutorService executorService, String startCommand, int startDelay) {
    executorService.schedule(() -> {
      try {
        new ProcessRunnerImpl().start(startCommand);
      } catch (IOException e) {
        logger.error(e.getMessage());
      }
    }, startDelay, TimeUnit.SECONDS);
  }

  private void startDbServers(ScheduledExecutorService executorService, String host, int port, int startDelay) throws Exception {
    startServers(executorService, DBSERVER_PROPERTIES_NAME, host, port, startDelay);
  }

  private void startFrontendServers(ScheduledExecutorService executorService, String host, int port, int startDelay) throws Exception {
    startServers(executorService, FRONTENDSERVER_PROPERTIES_NAME, host, port, startDelay);
  }

  private void startServers(ScheduledExecutorService executorService, String propertiesName, String host, int port, int startDelay) throws Exception {
    Properties properties = PropertiesHelper.getProperies(propertiesName);

    int serverInstanceCount = Integer.valueOf(properties.getProperty("instance.count"));
    String serverJarPath = properties.getProperty("jar.path");
    String name = properties.getProperty("name");

    logger.info(name + " instance count = "+serverInstanceCount);
    logger.info(name + " jar path = "+serverJarPath);

    for (int num = 1; num <= serverInstanceCount; num++) {
      StringBuilder sb = new StringBuilder();
      sb.append("java -jar ")
          .append("-DlogFilename=").append(name).append("-").append(num).append(".log ")
          .append(serverJarPath)
          .append(" ")
          .append(host)
          .append(" ")
          .append(port)
          .append(" ")
          .append(num);

      startClient(executorService, sb.toString(), startDelay);
      logger.info("Started "+name+" # "+num);
      Thread.sleep(100);
    }
  }
}
