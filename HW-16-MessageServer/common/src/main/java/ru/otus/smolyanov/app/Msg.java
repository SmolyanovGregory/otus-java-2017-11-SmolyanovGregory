package ru.otus.smolyanov.app;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 (message server)
 */
public abstract class Msg {
  public static final String CLASS_NAME_VARIABLE = "className";
  private final Address from;
  private final Address to;
  private final String className;

  protected Msg(Class<?> klass, Address from, Address to) {
    this.className = klass.getName();
    this.from = from;
    this.to = to;
  }

  protected Msg(Class<?> klass) {
    this.className = klass.getName();
    this.from = Address.UNDEFINED;
    this.to = Address.UNDEFINED;
  }
  public Address getFrom() {
    return from;
  }

  public Address getTo() {
    return to;
  }
}
