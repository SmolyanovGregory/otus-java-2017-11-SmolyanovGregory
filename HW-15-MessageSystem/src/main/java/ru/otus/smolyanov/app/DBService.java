package ru.otus.smolyanov.app;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 15 (message system)
 */

import ru.otus.smolyanov.base.*;
import ru.otus.smolyanov.cacheservice.CacheService;
import ru.otus.smolyanov.messageSystem.Addressee;

import java.util.List;

public interface DBService extends AutoCloseable, Addressee {

  void init();

  CacheService<DataSet> getCache();

  void saveChatMessage(ChatMessageDataSet chatMessageDataSet);

  List<ChatMessageDataSet> getAllChatMessage();
}
