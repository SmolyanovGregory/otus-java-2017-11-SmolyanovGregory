package ru.otus.smolyanov.accountservice;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 15 (message system)
 */

public class AccountServiceImpl implements AccountService {

  private final String ADMIN_LOGIN = "admin";
  private final String ADMIN_PSW = "admin";

  @Override
  public boolean isSignIn(String login, String psw) {
    // если догин и пароль не пустые, то пусть для простоты это будет авторизованный пользователь.
    return !login.isEmpty() && !psw.isEmpty();
  }

  @Override
  public boolean isAdmin(String login, String psw) {
    return login.equals(ADMIN_LOGIN) && psw.equals(ADMIN_PSW);
  }
}
