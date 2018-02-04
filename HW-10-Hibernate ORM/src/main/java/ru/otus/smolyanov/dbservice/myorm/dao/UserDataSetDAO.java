package ru.otus.smolyanov.dbservice.myorm.dao;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 10 - Hibernate ORM
 */

import ru.otus.smolyanov.base.UserDataSet;
import ru.otus.smolyanov.dbservice.myorm.executor.Executor;
import ru.otus.smolyanov.dbservice.myorm.executor.ExecutorImpl;

import java.sql.Connection;
import java.util.List;

public class UserDataSetDAO {

  private Connection connection;

  public UserDataSetDAO(Connection connection) {
    this.connection = connection;
  }

  public void save(UserDataSet user){
    Executor executor = new ExecutorImpl(connection);
    executor.save(user);
  }

  public UserDataSet load(long id){
    Executor executor = new ExecutorImpl(connection);
    return executor.load(id, UserDataSet.class);
  }

  public List<UserDataSet> loadAll(){
    Executor executor = new ExecutorImpl(connection);
    return executor.loadAll(UserDataSet.class);
  }
}
