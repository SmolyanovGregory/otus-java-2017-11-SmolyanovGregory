package ru.otus.smolyanov.dbservice;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 09 - my ORM
 */

import ru.otus.smolyanov.base.*;
import ru.otus.smolyanov.executor.Executor;
import ru.otus.smolyanov.executor.ExecutorImpl;
import ru.otus.smolyanov.executor.SQLHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class DBServiceImpl implements DBService{

  private final Connection connection;
  private Set<Class> createdTables = new HashSet();

  public DBServiceImpl() {
    this.connection = ConnectionHelper.getConnection();
  }

  private void checkTableExists(Class clazz) throws SQLException {
    if (!createdTables.contains(clazz)) {
      createTable(clazz);
      createdTables.add(clazz);
    }
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

  public Connection getConnection() {
    return connection;
  }

  @Override
  public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
    Executor executor = new ExecutorImpl(getConnection());
    checkTableExists(clazz);
    return executor.load(id, clazz);
  }

  @Override
  public <T extends DataSet> List<T> loadAll(Class<T> clazz) throws SQLException {
    Executor executor = new ExecutorImpl(getConnection());
    checkTableExists(clazz);
    return executor.loadAll(clazz);
  }

  @Override
  public UserDataSet getUser(long id) throws SQLException {
    return load(id, UserDataSet.class);
  }

  @Override
  public List<UserDataSet> getAllUsers() throws SQLException {
    return loadAll(UserDataSet.class);
  }

  @Override
  public BankAccountDataSet getBankAccount(long id) throws SQLException {
    return load(id, BankAccountDataSet.class);
  }

  @Override
  public List<BankAccountDataSet> getAllBankAccounts() throws SQLException {
    return loadAll(BankAccountDataSet.class);
  }

  @Override
  public <T extends DataSet> void save(T dataSet) throws SQLException {
    Executor executor = new ExecutorImpl(getConnection());
    checkTableExists(dataSet.getClass());
    executor.save(dataSet);
  }
}
