package ru.otus.smolyanov.dbservice;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.smolyanov.base.*;
import ru.otus.smolyanov.cacheservice.*;
import ru.otus.smolyanov.dbservice.dao.ChatMessageDataSetDAO;
import ru.otus.smolyanov.util.PropertiesHelper;
import java.util.List;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 16 - Message server
 */


public class DBServiceCachedImpl implements DBService {
  private static final Logger logger = LogManager.getLogger(DBServiceCachedImpl.class.getName());
  private static final String HIBERNATE_PROPERTIES_NAME = "hibernate.properties";
  private static final String CACHE_PROPERTIES_NAME = "cache.properties";

  private final SessionFactory sessionFactory;
  private final CacheService<DataSet> cache;

  public DBServiceCachedImpl() {

    Configuration configuration = new Configuration();

    configuration.addAnnotatedClass(ChatMessageDataSet.class);

    configuration.addProperties(PropertiesHelper.getProperies(HIBERNATE_PROPERTIES_NAME));

    sessionFactory = createSessionFactory(configuration);

    Properties cacheProperties = PropertiesHelper.getProperies(CACHE_PROPERTIES_NAME);

    int maxSize = Integer.valueOf(cacheProperties.getProperty(CacheServiceImpl.MAX_SIZE));
    int lifeTimeMs = Integer.valueOf(cacheProperties.getProperty(CacheServiceImpl.lIFE_TIME_MS));
    int idleTimeMs = Integer.valueOf(cacheProperties.getProperty(CacheServiceImpl.IDLE_TIME_MS));
    boolean isEternal = Boolean.valueOf(cacheProperties.getProperty(CacheServiceImpl.IS_ETERNAL));

    cache = new CacheServiceImpl.Builder<DataSet>(maxSize)
        .lifeTimeMs(lifeTimeMs)
        .idleTimeMs(idleTimeMs)
        .isEternal(isEternal)
        .build();
  }

  @Override
  public void close() throws Exception {
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

  public CacheService<DataSet> getCache() {
    return cache;
  }

  @Override
  public void saveChatMessage(ChatMessageDataSet chatMessageDataSet) {
    try (Session session = sessionFactory.openSession()) {
      new ChatMessageDataSetDAO(session).save(chatMessageDataSet);
    }
    cache.put(getCacheKey(chatMessageDataSet), chatMessageDataSet);
  }

  @Override
  public List<ChatMessageDataSet> getAllChatMessage() {
    try (Session session = sessionFactory.openSession()) {
      List<ChatMessageDataSet> result = new ChatMessageDataSetDAO(session).loadAll();
      // store in cache
      for (ChatMessageDataSet msg : result) {
        cache.put(getCacheKey(msg), msg);
      }

      return result;
    }
  }
}
