package ru.otus.smolyanov.main;

import ru.otus.smolyanov.accountservice.*;
import ru.otus.smolyanov.dbservice.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.smolyanov.servlets.*;
import java.util.Properties;
import ru.otus.smolyanov.util.PropertiesHelper;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 12 - Web server
 */

public class Main {

  private static final String APP_PROPERTIES_NAME = "application.properties";
  private static final String PUBLIC_HTML = "public_html";

  public static void main(String ... args) {
    try {
      new Main().run();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void run() throws Exception {
    Properties appProperties = PropertiesHelper.getProperies(APP_PROPERTIES_NAME);
    int port = Integer.valueOf(appProperties.getProperty("port"));
    int userGeneratorPeriodMs = Integer.valueOf(appProperties.getProperty("user_generator_period_ms"));

    ResourceHandler resourceHandler = new ResourceHandler();
    resourceHandler.setResourceBase(PUBLIC_HTML);

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

    AccountService accountService = new AccountServiceImpl();
    DBService dbService = new DBServiceCachedImpl();

    context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signin");
    context.addServlet(new ServletHolder(new UserServlet(accountService)), "/user");
    context.addServlet(new ServletHolder(new AdminServlet(accountService, dbService.getCache())), "/admin");

    Server server = new Server(port);
    server.setHandler(new HandlerList(resourceHandler, context));

    server.start();

    System.out.println("******** web server started on "+port+" port ********");

    UserDataSetGenerator dataGenerator = new UserDataSetGenerator(dbService, userGeneratorPeriodMs);
    dataGenerator.run();

    server.join();

    dbService.close();
  }
}