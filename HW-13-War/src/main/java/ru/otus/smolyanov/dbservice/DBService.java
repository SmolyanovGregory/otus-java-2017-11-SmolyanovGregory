package ru.otus.smolyanov.dbservice;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 13 - web archive
 */

import ru.otus.smolyanov.base.*;
import ru.otus.smolyanov.cacheservice.CacheService;

import java.util.List;

public interface DBService extends AutoCloseable {

  String getMetaData();

  UserDataSet getUser(long id);

  List<UserDataSet> getAllUsers();

  void saveUser(UserDataSet user);

  CacheService<DataSet> getCache();
}
