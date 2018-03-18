package ru.otus.smolyanov.datasetgenerator;

import ru.otus.smolyanov.base.UserDataSet;
import ru.otus.smolyanov.dbservice.DBService;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 15 (message system)
 */

public class UserDataSetGenerator extends Thread{

  private final DBService dbService;
  private final int sleepTime;
  private long firstSavedUserId = 0;
  private volatile boolean isGenerating = false;

  public UserDataSetGenerator(DBService dbService, int sleepTime) {
    this.dbService = dbService;
    this.sleepTime = sleepTime;
  }

  @Override
  public void run() {
    while (true) {
      if (isGenerating) {
        UserDataSet user = createRandomUserDataSet();
        dbService.saveUser(user);

        if (firstSavedUserId == 0) {
          firstSavedUserId = user.getId();
        }

        // random read between created users
        UserDataSet anotherUser = dbService.getUser(getRandomUserId(firstSavedUserId, user.getId()));
      }

      try {
        Thread.sleep(sleepTime);
      } catch (InterruptedException e) {
        break;
      }
    }
  }

  private UserDataSet createRandomUserDataSet() {
    int age = (int) (Math.random() * 50) + 10;
    return new UserDataSet(getRandomUserName(), age, null, null);
  }

  private String getRandomUserName() {
    int userNameLength = (int) (Math.random() * 15) + 5;

    StringBuilder sb = new StringBuilder();
    for (int i = 1; i <= userNameLength; i++) {
      // from a to z
      int rndAsciiCode = (int)(Math.random() * 25) + 97;
      sb.append((char)(rndAsciiCode));
    }
    return sb.toString();
  }

  private long getRandomUserId(long from, long to) {
    return (long) (Math.random() * (to - from))+from;
  }

  public void startGenerating() {
    isGenerating = true;
  }

  public void stopGenerating() {
    isGenerating = false;
  }

  public boolean isGenerating() {
    return isGenerating;
  }

}
