package ru.otus.smolyanov.dbservice.hibernate;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 10 - Hibernate ORM
 */

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import ru.otus.smolyanov.base.*;
import ru.otus.smolyanov.dbservice.DBService;
import ru.otus.smolyanov.dbservice.hibernate.dao.UserDataSetDAO;
import java.util.List;
import java.util.Map;

public class DBServiceHibernateImpl implements DBService {

  private final SessionFactory sessionFactory;

  public DBServiceHibernateImpl() {
    Configuration configuration = new Configuration();

    configuration.addAnnotatedClass(UserDataSet.class);
    configuration.addAnnotatedClass(PhoneDataSet.class);
    configuration.addAnnotatedClass(AddressDataSet.class);

    configuration.setProperty(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
    configuration.setProperty(Environment.DRIVER, "org.h2.Driver");
    configuration.setProperty(Environment.URL, "jdbc:h2:tcp://localhost/~/test");
    configuration.setProperty(Environment.USER, "test");
    configuration.setProperty(Environment.PASS, "test");
    configuration.setProperty(Environment.SHOW_SQL, "false");
    configuration.setProperty(Environment.HBM2DDL_AUTO, "validate");
    configuration.setProperty(Environment.ENABLE_LAZY_LOAD_NO_TRANS, "true");

    sessionFactory = createSessionFactory(configuration);
  }

  @Override
  public String getMetaData() {
    Map<String, Object> properties = sessionFactory.getProperties();
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("Connected to: ")
        .append(properties.get(Environment.URL).toString())
        .append("\n")
        .append("Driver: ")
        .append(properties.get(Environment.DRIVER).toString());

    return stringBuilder.toString();
  }

  @Override
  public UserDataSet getUser(long id) {
    try (Session session = sessionFactory.openSession()) {
      return new UserDataSetDAO(session).load(id);
    }
  }

  @Override
  public List<UserDataSet> getAllUsers() {
    try (Session session = sessionFactory.openSession()) {
      return new UserDataSetDAO(session).loadAll();
    }
  }

  @Override
  public void saveUser(UserDataSet user) {
    try (Session session = sessionFactory.openSession()) {
      new UserDataSetDAO(session).save(user);
    }
  }

  @Override
  public void close() throws Exception {
    sessionFactory.close();
  }

  private static SessionFactory createSessionFactory(Configuration configuration) {
    StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
    builder.applySettings(configuration.getProperties());
    ServiceRegistry serviceRegistry = builder.build();
    return configuration.buildSessionFactory(serviceRegistry);
  }
}
