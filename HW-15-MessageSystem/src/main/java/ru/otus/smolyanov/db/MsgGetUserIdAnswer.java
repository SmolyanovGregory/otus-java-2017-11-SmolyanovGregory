package ru.otus.smolyanov.db;

import ru.otus.smolyanov.app.FrontendService;
import ru.otus.smolyanov.app.MsgToFrontend;
import ru.otus.smolyanov.messageSystem.Address;

/**
 * Created by tully.
 */
public class MsgGetUserIdAnswer extends MsgToFrontend {
    private final String name;
    private final int id;

    public MsgGetUserIdAnswer(Address from, Address to, String name, int id) {
        super(from, to);
        this.name = name;
        this.id = id;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.addUser(id, name);
    }
}
