package ru.otus.smolyanov.app;

import ru.otus.smolyanov.messageSystem.Address;
import ru.otus.smolyanov.messageSystem.Addressee;
import ru.otus.smolyanov.messageSystem.Message;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 15 (message system)
 */

public abstract class MsgToDB extends Message {
  public MsgToDB(Address from, Address to) {
    super(from, to);
  }

  @Override
  public void exec(Addressee addressee) {
    if (addressee instanceof DBService) {
      exec((DBService) addressee);
    }
  }

  public abstract void exec(DBService dbService);
}
