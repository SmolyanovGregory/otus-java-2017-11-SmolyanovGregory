package ru.otus.smolyanov.messageSystem;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 15 (message system)
 */

public interface Addressee {
  Address getAddress();

  MessageSystem getMS();
}
