package ru.otus.smolyanov.dbservice;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 10 - Hibernate ORM
 */

import ru.otus.smolyanov.base.*;
import java.util.List;

public interface DBService extends AutoCloseable {

  String getMetaData();

  UserDataSet getUser(long id);

  List<UserDataSet> getAllUsers();

  void saveUser(UserDataSet user);
}
