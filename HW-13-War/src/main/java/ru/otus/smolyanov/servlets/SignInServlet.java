package ru.otus.smolyanov.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 13 - web archive
 */

public class SignInServlet extends BaseServlet {

  private static final String NOT_AUTHORIZED = "You are not authorized.";

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html;charset=utf-8");

    String login = request.getParameter(LOGIN_PARAMETER_NAME);
    String password = request.getParameter(PSW_PARAMETER_NAME);

    if (accountService.isSignIn(login, password)) {

      request.getSession().setAttribute(LOGIN_PARAMETER_NAME, login);
      request.getSession().setAttribute(PSW_PARAMETER_NAME, password);

      if (accountService.isAdmin(login, password)) {
        response.sendRedirect(ADMIN_RES);
      } else {
        response.sendRedirect(USER_RES);
      }
    } else {
      response.getWriter().println(NOT_AUTHORIZED);
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}
