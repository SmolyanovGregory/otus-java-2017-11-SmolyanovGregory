package ru.otus.smolyanov.accountservice;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 15 (message system)
 */

public interface AccountService {
  boolean isSignIn(String login, String psw);
  boolean isAdmin(String login, String psw);
}
