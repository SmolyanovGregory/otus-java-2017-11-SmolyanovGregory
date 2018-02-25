package ru.otus.smolyanov.accountservice;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 13 - web archive
 */

public interface AccountService {
  boolean isSignIn(String login, String psw);
  boolean isAdmin(String login, String psw);
}
