package ru.otus.smolyanov.app;

import ru.otus.smolyanov.messageSystem.Address;
import ru.otus.smolyanov.messageSystem.MessageSystem;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 15 (message system)
 */

public class MessageSystemContext {
  private final MessageSystem messageSystem;

  private Address dbAddress;
  private Address chatAddress;

  public MessageSystemContext(MessageSystem messageSystem) {
    this.messageSystem = messageSystem;
  }

  public MessageSystem getMessageSystem() {
    return messageSystem;
  }

  public Address getDbAddress() {
    return dbAddress;
  }

  public void setDbAddress(Address dbAddress) {
    this.dbAddress = dbAddress;
  }

  public Address getChatAddress() {
    return chatAddress;
  }

  public void setChatAddress(Address chatAddress) {
    this.chatAddress = chatAddress;
  }
}
