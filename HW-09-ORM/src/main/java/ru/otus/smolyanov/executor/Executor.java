package ru.otus.smolyanov.executor;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 09 - my ORM
 */

import ru.otus.smolyanov.base.DataSet;
import java.sql.SQLException;
import java.util.List;

public interface Executor {

  // Return any auto-generated keys created as a result of executing SQL statement.
  List<Long> exec(String sqlStatement) throws SQLException;

  <T extends DataSet> void save(T dataSet);

  <T extends DataSet> T load(long id, Class<T> clazz);

  <T extends DataSet> List<T> loadAll(Class<T> clazz);

  <T> T execQuery(String sqlStatement, ResultHandler<T> handler) throws SQLException;
}
