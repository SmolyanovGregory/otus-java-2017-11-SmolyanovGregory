package ru.otus.smolyanov.db;

import ru.otus.smolyanov.app.DBService;
import ru.otus.smolyanov.app.MsgToDB;
import ru.otus.smolyanov.messageSystem.Address;
import ru.otus.smolyanov.messageSystem.MessageSystem;

/**
 * Created by tully.
 */
public class MsgGetUserId extends MsgToDB {
    private final String login;

    public MsgGetUserId(Address from, Address to, String login) {
        super(from, to);
        this.login = login;
    }

    @Override
    public void exec(DBService dbService) {
        int id = dbService.getUserId(login);
        dbService.getMS().sendMessage(new MsgGetUserIdAnswer(getTo(), getFrom(), login, id));
    }
}
