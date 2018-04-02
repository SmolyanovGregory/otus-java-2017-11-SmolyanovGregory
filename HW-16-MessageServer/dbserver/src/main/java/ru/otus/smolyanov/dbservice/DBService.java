package ru.otus.smolyanov.dbservice;

import ru.otus.smolyanov.base.*;
import ru.otus.smolyanov.cacheservice.CacheService;
import java.util.List;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 - Message server
 */

public interface DBService extends AutoCloseable {

  //void init();

  CacheService<DataSet> getCache();

  void saveChatMessage(ChatMessageDataSet chatMessageDataSet);

  List<ChatMessageDataSet> getAllChatMessage();
}
