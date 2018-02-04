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
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class DBServiceImpl implements DBService {

  private final Connection connection;
  private Set<String> existingTables = new HashSet();

  public DBServiceImpl() {
    this.connection = ConnectionHelper.getConnection();

    // filling the existing table list
    for (String tableName : getExistingTables()) {
      existingTables.add(tableName.toUpperCase());
    }
  }

  private void checkTableExists(Class clazz) {
    try {
      if (!existingTables.contains(clazz.getSimpleName().toUpperCase())) {
        // creating table
        createTable(clazz);
        // add the table name to the existing tables list
        existingTables.add(clazz.getSimpleName().toUpperCase());
      }
    } catch (SQLException e) {
      e.printStackTrace();
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

  private Connection getConnection() {
    return connection;
  }

  @Override
  public UserDataSet getUser(long id) {
    checkTableExists(UserDataSet.class);
    return new UserDataSetDAO(getConnection()).load(id);
  }

  @Override
  public List<UserDataSet> getAllUsers() {
    checkTableExists(UserDataSet.class);
    return new UserDataSetDAO(getConnection()).loadAll();
  }

  private List<String> getExistingTables() {
    Executor executor = new ExecutorImpl(getConnection());
    List<String> result = null;

    try {
      result = executor.execQuery(SQLHelper.EXISTING_TABLES_LIST_SQL, r -> {
        List<String> tableNames = new LinkedList<>();

        while (r.next()) {
          tableNames.add(r.getString(1));
        }
        return tableNames;

      });
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public void saveUser(UserDataSet user) {
    checkTableExists(UserDataSet.class);
    new UserDataSetDAO(getConnection()).save(user);
  }
}
