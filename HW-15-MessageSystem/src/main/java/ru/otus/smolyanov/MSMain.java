package ru.otus.smolyanov;


import ru.otus.smolyanov.app.DBService;
import ru.otus.smolyanov.app.FrontendService;
import ru.otus.smolyanov.app.MessageSystemContext;
import ru.otus.smolyanov.db.DBServiceImpl;
import ru.otus.smolyanov.front.FrontendServiceImpl;
import ru.otus.smolyanov.messageSystem.Address;
import ru.otus.smolyanov.messageSystem.MessageSystem;

public class MSMain {
    public static void main(String[] args) throws InterruptedException {
        MessageSystem messageSystem = new MessageSystem();

        MessageSystemContext context = new MessageSystemContext(messageSystem);
        Address frontAddress = new Address("Frontend");
        context.setFrontAddress(frontAddress);
        Address dbAddress = new Address("DB");
        context.setDbAddress(dbAddress);

        FrontendService frontendService = new FrontendServiceImpl(context, frontAddress);
        frontendService.init();

        DBService dbService = new DBServiceImpl(context, dbAddress);
        dbService.init();

        messageSystem.start();

        //for test
        frontendService.handleRequest("tully");
        frontendService.handleRequest("sully");

        frontendService.handleRequest("tully");
        frontendService.handleRequest("sully");

        Thread.sleep(1000);
        messageSystem.dispose();
    }
}
