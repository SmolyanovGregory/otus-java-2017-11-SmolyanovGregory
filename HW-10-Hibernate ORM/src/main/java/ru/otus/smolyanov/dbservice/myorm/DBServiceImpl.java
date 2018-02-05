package ru.otus.smolyanov.dbservice.myorm;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 10 - Hibernate ORM
 */

import ru.otus.smolyanov.base.*;
import ru.otus.smolyanov.dbservice.DBService;
import ru.otus.smolyanov.dbservice.myorm.dao.UserDataSetDAO;
import ru.otus.smolyanov.dbservice.myorm.executor.Executor;
import ru.otus.smolyanov.dbservice.myorm.executor.ExecutorImpl;
import ru.otus.smolyanov.dbservice.myorm.helpers.ConnectionHelper;
import ru.otus.smolyanov.dbservice.myorm.helpers.SQLHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class DBServiceImpl implements DBService {

  private final Connection connection;
  private Set<Class> registeredEntity = new HashSet<>();

  public DBServiceImpl() {
    this.connection = ConnectionHelper.getConnection();

    registeredEntity.add(UserDataSet.class);

    // create tables if it not exists
    prepareSchema();
  }

  private void createTable(Class clazz) throws SQLException {
    new ExecutorImpl(getConnection()).exec(SQLHelper.getCreateTableStatement(clazz));
  }

  @Override
  public String getMetaData() {
    try {
      return new StringBuilder().append("Connected to: ")
          .append(getConnection().getMetaData().getURL())
          .append("\n")
          .append("DB name: ")
          .append(getConnection().getMetaData().getDatabaseProductName())
          .append("\n")
          .append("DB version: ")
          .append(getConnection().getMetaData().getDatabaseProductVersion())
          .append("\n")
          .append("Driver: ")
          .append(getConnection().getMetaData().getDriverName()).toString();
    } catch (SQLException e) {
      e.printStackTrace();
      return e.getMessage();
    }
  }

  @Override
  public void close() throws Exception {
    connection.close();
  }

  private Connection getConnection() {
    return connection;
  }

  @Override
  public UserDataSet getUser(long id) {
    return new UserDataSetDAO(getConnection()).load(id);
  }

  @Override
  public List<UserDataSet> getAllUsers() {
    return new UserDataSetDAO(getConnection()).loadAll();
  }

  @Override
  public void saveUser(UserDataSet user) {
    new UserDataSetDAO(getConnection()).save(user);
  }

  private void prepareSchema() {
    Executor executor = new ExecutorImpl(getConnection());
    for (Class klass : registeredEntity) {
      try {
        executor.exec(SQLHelper.getCreateTableStatement(klass));
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
