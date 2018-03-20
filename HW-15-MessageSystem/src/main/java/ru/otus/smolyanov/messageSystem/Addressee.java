package ru.otus.smolyanov.messageSystem;

/**
 * @author tully
 */
public interface Addressee {
    Address getAddress();

    MessageSystem getMS();
}
