package ru.otus.smolyanov.servlets;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.smolyanov.accountservice.AccountService;
import ru.otus.smolyanov.cacheservice.CacheService;
import ru.otus.smolyanov.config.AppConfig;
import ru.otus.smolyanov.datasetgenerator.UserDataSetGenerator;
import ru.otus.smolyanov.dbservice.DBService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 13 - web archive
 */

public class BaseServlet extends HttpServlet {

  static final String LOGIN_PARAMETER_NAME = "login";
  static final String PSW_PARAMETER_NAME = "password";
  static final String ADMIN_PAGE_TEMPLATE = "admin.html";
  static final String USER_RES = "user";
  static final String START_RES = "index.html";
  static final String ADMIN_RES = "admin";
  static final String USER_PAGE_TEMPLATE = "user.html";
  private static final String APPLICATION_CONTEXT_PARAM_NAME = "springApplicationContext";
  static final String USERS_GENERATING_STATUS = "users_generating_status";
  static final String STATUS_ON = "on";
  static final String STATUS_OFF = "off";

  ApplicationContext context;

  AccountService accountService;
  DBService dbService;
  CacheService cacheService;
  UserDataSetGenerator randomUserGenerator;

  public void init(ServletConfig config) {
    ServletContext servletContext = config.getServletContext();

    Object attribute = servletContext.getAttribute(APPLICATION_CONTEXT_PARAM_NAME);
    if (attribute == null) {
      context = new AnnotationConfigApplicationContext(AppConfig.class);
      servletContext.setAttribute(APPLICATION_CONTEXT_PARAM_NAME, context);
    } else {
      context = (ApplicationContext) attribute;
    }

    accountService = (AccountService) context.getBean("accountService");
    dbService = (DBService) context.getBean("dbService");
    cacheService = (CacheService) context.getBean("cacheService");
    randomUserGenerator = (UserDataSetGenerator) context.getBean("userDatasetGenerator");

    if (randomUserGenerator.getState().equals(Thread.State.NEW)) {
      randomUserGenerator.start();
    }
  }

}
