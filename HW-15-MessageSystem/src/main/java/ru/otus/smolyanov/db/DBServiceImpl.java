package ru.otus.smolyanov.db;

import ru.otus.smolyanov.app.DBService;
import ru.otus.smolyanov.app.MessageSystemContext;
import ru.otus.smolyanov.messageSystem.Address;
import ru.otus.smolyanov.messageSystem.Addressee;
import ru.otus.smolyanov.messageSystem.MessageSystem;

/**
 * Created by tully.
 */
public class DBServiceImpl implements DBService {
    private final Address address;
    private final MessageSystemContext context;

    public DBServiceImpl(MessageSystemContext context, Address address) {
        this.context = context;
        this.address = address;
    }

    public void init() {
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }

    public int getUserId(String name) {
        //todo: load id from db
        return name.hashCode();
    }
}
