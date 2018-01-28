package ru.otus.smolyanov.executor;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 09 - my ORM
 */

import ru.otus.smolyanov.base.DataSet;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ExecutorImpl implements Executor {

  private final Connection connection;

  public ExecutorImpl(Connection connection) {
    this.connection = connection;
  }

  public void exec(String sqlStatement) throws SQLException {
    try (Statement stmt = connection.createStatement()) {
      stmt.execute(sqlStatement);
    }
  }

  public <T> T execQuery(String query, ResultHandler<T> handler) throws SQLException {
    try(Statement stmt = connection.createStatement()) {
      stmt.execute(query);
      ResultSet result = stmt.getResultSet();
      return handler.handle(result);
    }
  }

  @Override
  public <T extends DataSet> T load(long id, Class<T> clazz) {
    String sql = String.format(SQLHelper.getSelectByIdStatement(), clazz.getSimpleName(), id);

    DataSet result = null;
    try {
      result = execQuery(sql, rs -> {
        if (rs.next()) {
          DataSet ds = ReflectionHelper.instantiate(clazz);

          // set key
          ReflectionHelper.setSuperClassFieldValue(ds, "id", rs.getLong("id"));
          // set other fields
          for (Field field : clazz.getDeclaredFields()) {
            ReflectionHelper.setFieldValue(ds, field.getName(), rs.getObject(field.getName()));
          }

          return ds;
        } else {
          return null;
        }
      });
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return (T) result;
  }

  @Override
  public <T extends DataSet> List<T> loadAll(Class<T> clazz) {
    String sql = String.format(SQLHelper.getSelectAllStatement(), clazz.getSimpleName());

    List<DataSet> result = null;
    try {
      result = execQuery(sql, rs -> {

        List<DataSet> list = new LinkedList<>();

        while (!rs.isLast()) {
          rs.next();

          DataSet ds = ReflectionHelper.instantiate(clazz);

          // set key
          ReflectionHelper.setSuperClassFieldValue(ds, "id", rs.getLong("id"));
          // set other fields
          for (Field field : clazz.getDeclaredFields()) {
            ReflectionHelper.setFieldValue(ds, field.getName(), rs.getObject(field.getName()));
          }

          list.add(ds);
        }

        return list;
      });

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return (List<T>) result;
  }

  @Override
  public <T extends DataSet> void save(T dataSet) {
    String sqlStatement = load(dataSet.getId(), dataSet.getClass()) == null ? SQLHelper.getInsertStatement(dataSet) : SQLHelper.getUpdateStatement(dataSet);
    try {
      exec(sqlStatement);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
