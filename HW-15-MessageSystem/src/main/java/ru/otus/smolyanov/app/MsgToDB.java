package ru.otus.smolyanov.app;

import ru.otus.smolyanov.app.DBService;
import ru.otus.smolyanov.messageSystem.Address;
import ru.otus.smolyanov.messageSystem.Addressee;
import ru.otus.smolyanov.messageSystem.Message;

/**
 * Created by tully.
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
