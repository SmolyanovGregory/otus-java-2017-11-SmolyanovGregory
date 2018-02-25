package ru.otus.smolyanov.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 13 - web archive
 */

public class UserServlet extends BaseServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html;charset=utf-8");

      Object login = request.getSession().getAttribute(LOGIN_PARAMETER_NAME);
      Object password = request.getSession().getAttribute(PSW_PARAMETER_NAME);

      if (login != null && password != null) {
        boolean loggedIn = accountService.isSignIn(login.toString(), password.toString());

        if (loggedIn) {
          Map<String, Object> pageVariables = new HashMap<>();
          pageVariables.put(LOGIN_PARAMETER_NAME, login);

          response.getWriter().println(TemplateProcessor.instance().getPage(USER_PAGE_TEMPLATE, pageVariables));

          response.setStatus(HttpServletResponse.SC_OK);
        } else {
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          response.sendRedirect(START_RES);
        }
      } else {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.sendRedirect(START_RES);
      }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
    }
}
