package ru.otus.smolyanov.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 (message server)
 */

@Entity
@Table(name = "CHATMESSAGE")
public class ChatMessageDataSet extends DataSet {

  @Column(name = "user")
  private String userName;
  @Column(name = "message")
  private String messageBody;

  public ChatMessageDataSet() {
  }

  public ChatMessageDataSet(String userName, String messageBody) {
    this.userName = userName;
    this.messageBody = messageBody;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getMessageBody() {
    return messageBody;
  }

  public void setMessageBody(String messageBody) {
    this.messageBody = messageBody;
  }
}
