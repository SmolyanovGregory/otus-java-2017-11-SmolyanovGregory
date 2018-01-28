package ru.otus.smolyanov.dbservice;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 09 - my ORM
 */

import ru.otus.smolyanov.base.*;
import java.sql.SQLException;
import java.util.List;

public interface DBService extends AutoCloseable {

  String getMetaData();

  <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException;

  <T extends DataSet> List<T> loadAll(Class<T> clazz) throws SQLException;

  UserDataSet getUser(long id) throws SQLException;

  List<UserDataSet> getAllUsers() throws SQLException;

  BankAccountDataSet getBankAccount(long id) throws SQLException;

  List<BankAccountDataSet> getAllBankAccounts() throws SQLException;

  <T extends DataSet> void save(T dataSet) throws SQLException;
}
