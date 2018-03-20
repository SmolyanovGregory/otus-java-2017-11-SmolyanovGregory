package ru.otus.smolyanov.app;

import ru.otus.smolyanov.messageSystem.Address;
import ru.otus.smolyanov.messageSystem.Addressee;
import ru.otus.smolyanov.messageSystem.Message;

/**
 * Created by tully.
 */
public abstract class MsgToFrontend extends Message {
    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof FrontendService) {
            exec((FrontendService) addressee);
        } else {
            //todo error!
        }
    }

    public abstract void exec(FrontendService frontendService);
}