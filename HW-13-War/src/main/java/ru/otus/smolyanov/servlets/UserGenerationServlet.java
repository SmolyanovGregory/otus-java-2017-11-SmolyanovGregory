package ru.otus.smolyanov.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import ru.otus.smolyanov.datasetgenerator.*;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 13 - web archive
 */

public class UserGenerationServlet extends BaseServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html;charset=utf-8");

    Object login = request.getSession().getAttribute(LOGIN_PARAMETER_NAME);
    Object password = request.getSession().getAttribute(PSW_PARAMETER_NAME);

    if (login != null && password != null) {

      response.setStatus(HttpServletResponse.SC_OK);

      if (accountService.isAdmin(login.toString(), password.toString())) {

        // start generating a random user thread
        Object threadObject = request.getSession().getAttribute(USERS_GENERATING_THREAD_PARAM_NAME);
        if (threadObject == null) {
          // start thread
          UserDataSetGenerator dataGenerator = new UserDataSetGenerator(dbService, 3000);
          Thread userGeneratingThread = new Thread(dataGenerator);
          userGeneratingThread.start();
          request.getSession().setAttribute(USERS_GENERATING_THREAD_PARAM_NAME, userGeneratingThread);
        } else {
          // stop thread
          ((Thread) threadObject).interrupt();
          request.getSession().removeAttribute(USERS_GENERATING_THREAD_PARAM_NAME);
        }

        response.sendRedirect(ADMIN_RES);
      } else {
        response.sendRedirect(USER_RES);
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
