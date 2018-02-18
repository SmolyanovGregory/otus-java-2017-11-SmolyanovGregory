package ru.otus.smolyanov.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 12 - Web server
 */

public abstract class BaseServlet extends HttpServlet {

  static final String ADMIN_PAGE_TEMPLATE = "admin.html";
  static final String LOGIN_PARAMETER_NAME = "login";
  static final String PSW_PARAMETER_NAME = "password";
  static final String USER_RES = "user";
  static final String START_RES = "index.html";
  static final String ADMIN_RES = "admin";
  static final String USER_PAGE_TEMPLATE = "user.html";

  public abstract void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
  public abstract void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
