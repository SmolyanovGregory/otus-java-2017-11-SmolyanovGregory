package ru.otus.smolyanov.app;

import ru.otus.smolyanov.messageSystem.Addressee;

/**
 * Created by tully.
 */
public interface FrontendService extends Addressee {
    void init();

    void handleRequest(String login);

    void addUser(int id, String name);
}

