package ru.otus.smolyanov.servlets;

import ru.otus.smolyanov.accountservice.AccountService;
import ru.otus.smolyanov.cacheservice.CacheService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 12 - Web server
 */

public class AdminServlet extends BaseServlet {

  private final AccountService accountService;
  private final CacheService cacheService;

  public AdminServlet(AccountService accountService, CacheService cacheService) {
    this.accountService = accountService;
    this.cacheService = cacheService;
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html;charset=utf-8");

    Object login = request.getSession().getAttribute(LOGIN_PARAMETER_NAME);
    Object password = request.getSession().getAttribute(PSW_PARAMETER_NAME);

    if (login != null && password != null) {

      response.setStatus(HttpServletResponse.SC_OK);

      if (accountService.isAdmin(login.toString(), password.toString())) {

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(LOGIN_PARAMETER_NAME, login.toString());
        pageVariables.put("cacheSize", cacheService.getSize());
        pageVariables.put("hitCount", cacheService.getHitCount());
        pageVariables.put("missCount", cacheService.getMissCount());

        response.getWriter().println(TemplateProcessor.instance().getPage(ADMIN_PAGE_TEMPLATE, pageVariables));
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
