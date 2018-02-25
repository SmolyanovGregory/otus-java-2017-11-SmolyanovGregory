package ru.otus.smolyanov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import ru.otus.smolyanov.accountservice.AccountService;
import ru.otus.smolyanov.accountservice.AccountServiceImpl;
import ru.otus.smolyanov.cacheservice.CacheService;
import ru.otus.smolyanov.dbservice.DBService;
import ru.otus.smolyanov.dbservice.DBServiceCachedImpl;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 13 - web archive
 */

@Configuration
public class AppConfig {

  @Bean(name = "accountService")
  @Description("Provides an account service")
  public AccountService accountService() {
    return new AccountServiceImpl();
  }

  @Bean(name = "dbService")
  @Description("Provides a database service")
  public DBService dbService() {
    return new DBServiceCachedImpl();
  }

  @Bean(name = "cacheService")
  @Description("Provides a cache service")
  public CacheService cacheService() {
    return dbService().getCache();
  }
}
