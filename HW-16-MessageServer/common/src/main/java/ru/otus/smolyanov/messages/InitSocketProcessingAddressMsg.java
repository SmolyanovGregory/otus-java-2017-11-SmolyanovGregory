package ru.otus.smolyanov.messages;

import ru.otus.smolyanov.app.Msg;
import ru.otus.smolyanov.app.Address;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 - Message server
 */

public class InitSocketProcessingAddressMsg extends Msg {

  private final Address processingAddress;
  private final int identifier;

  public InitSocketProcessingAddressMsg(Address processingAddress, int identifier) {
    super(InitSocketProcessingAddressMsg.class);
    this.processingAddress = processingAddress;
    this.identifier = identifier;
  }

  public Address getProcessingAddress() {
    return processingAddress;
  }

  public int getIdentifier() {
    return identifier;
  }

  @Override
  public String toString() {
    return "InitWorkerProcessingAddressMsg{processingAddress=" + processingAddress +", identifier="+ identifier +'}';
  }

}
