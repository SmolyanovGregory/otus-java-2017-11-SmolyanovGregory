package ru.otus.smolyanov.accountservice;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 12 - Web server
 */

public interface AccountService {
  boolean isSignIn(String login, String psw);
  boolean isAdmin(String login, String psw);
}
