package ru.otus.smolyanov.app;

import ru.otus.smolyanov.messageSystem.Addressee;

/**
 * Created by tully.
 */
public interface DBService extends Addressee {
    void init();

    int getUserId(String name);
}
