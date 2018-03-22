package ru.otus.smolyanov.app;

import ru.otus.smolyanov.messageSystem.Address;
import ru.otus.smolyanov.messageSystem.Addressee;
import ru.otus.smolyanov.messageSystem.Message;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 15 (message system)
 */

public abstract class MsgToChat extends Message {
  public MsgToChat(Address from, Address to) {
    super(from, to);
  }

  @Override
  public void exec(Addressee addressee) {
    if (addressee instanceof ChatService) {
      exec((ChatService) addressee);
    }
  }

  public abstract void exec(ChatService chatService);
}