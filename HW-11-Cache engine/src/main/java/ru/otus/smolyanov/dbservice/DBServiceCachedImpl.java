package ru.otus.smolyanov.dbservice;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 11 - My cache engine
 */

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import ru.otus.smolyanov.base.*;
import ru.otus.smolyanov.dbservice.dao.UserDataSetDAO;
import ru.otus.smolyanov.cacheservice.*;
import java.util.List;
import java.util.Map;

public class DBServiceCachedImpl implements DBService {

  private static final int MAX_CACHE_ELEMENTS_COUNT = 15;

  private final SessionFactory sessionFactory;
  private final CacheService<DataSet> cache;

  public DBServiceCachedImpl() {
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

    cache = new CacheServiceImpl.Builder<DataSet>(MAX_CACHE_ELEMENTS_COUNT).build();
  }

  @Override
  public String getMetaData() {
    Map<String, Object> properties = sessionFactory.getProperties();
    return "Connected to: " +
        properties.get(Environment.URL).toString() +
        "\n" +
        "Driver: " +
        properties.get(Environment.DRIVER).toString();
  }

  @Override
  public UserDataSet getUser(long id) {
    DataSet user = cache.get(getCacheKey(id, UserDataSet.class));

    if (user != null) {
      System.out.println("--> read object from cache:");
      return (UserDataSet) user;
    } else {
      try (Session session = sessionFactory.openSession()) {
        System.out.println("==> read object from DB:");
        return new UserDataSetDAO(session).load(id);
      }
    }
  }

  @Override
  public List<UserDataSet> getAllUsers() {
    try (Session session = sessionFactory.openSession()) {
      List<UserDataSet> result = new UserDataSetDAO(session).loadAll();
      // store in cache
      for (UserDataSet user : result) {
        cache.put(getCacheKey(user), user);
      }

      return result;
    }
  }

  @Override
  public void saveUser(UserDataSet user) {
    try (Session session = sessionFactory.openSession()) {
      new UserDataSetDAO(session).save(user);
    }
    cache.put(getCacheKey(user), user);
  }

  @Override
  public void close() throws Exception {
    System.out.println("Cache statisics: hit count = "+cache.getHitCount()+"; miss count = "+cache.getMissCount());
    cache.dispose();
    sessionFactory.close();
  }

  private static SessionFactory createSessionFactory(Configuration configuration) {
    StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
    builder.applySettings(configuration.getProperties());
    ServiceRegistry serviceRegistry = builder.build();
    return configuration.buildSessionFactory(serviceRegistry);
  }

  private ElementKey getCacheKey(DataSet dataSet) {
    return new ElementKey(dataSet.getId(), dataSet.getClass());
  }

  private ElementKey getCacheKey(long id, Class klass) {
    return new ElementKey(id, klass);
  }
}
