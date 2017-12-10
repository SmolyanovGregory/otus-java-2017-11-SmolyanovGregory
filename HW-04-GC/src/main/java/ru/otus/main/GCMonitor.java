package ru.otus.main;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 04 - GC
 */

import java.lang.management.GarbageCollectorMXBean;
import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import com.sun.management.GarbageCollectionNotificationInfo;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

/*
* Отслеживает работу garbage collectors и собирает статистику по их срабатываниям
* */
public class GCMonitor {

  private int statisticsPeriodLength; // длина периода статистики в секундах
  private static NotificationListener gcHandler;
  private Map<Integer, Map<String, GCStatistic>> statistics;
  private int statisticsCurrentPeriodNum;
  private Logger logger;

  public GCMonitor(int statisticsPeriodLength, Logger logger) {
    this.statisticsPeriodLength = statisticsPeriodLength;
    this.logger = logger;

    this.statistics = new HashMap<Integer, Map<String, GCStatistic>>();
    this.statisticsCurrentPeriodNum = 1;

    gcHandler = new NotificationListener() {
      @Override
      public void handleNotification(Notification notification, Object handback) {
        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
          GarbageCollectionNotificationInfo gcInfo = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

          // логирование информации о срабатывании GC
          //logGCEventInfo(gcInfo);

          // обновление статистики
          int p = getStatisticsPeriodNum(gcInfo.getGcInfo().getStartTime());
          updateStatistics(gcInfo.getGcName(), gcInfo.getGcInfo().getDuration(), p);

          // логирование статистики за прошедший период
          if (p > statisticsCurrentPeriodNum) {
            saveStatisticsForPeriod(statisticsCurrentPeriodNum);
            statisticsCurrentPeriodNum++;
          }
        }
      }
    };
  }

  /*
  * запуск сбора статистики по срабатываниям GC
  * */
  public void start() {
    String gcList ="";
    for (GarbageCollectorMXBean gcBean : java.lang.management.ManagementFactory.getGarbageCollectorMXBeans()) {
      ((NotificationEmitter) gcBean).addNotificationListener(gcHandler, null, null);
      if (!gcList.equalsIgnoreCase("")) {
        gcList += ", ";
      }
      gcList += "'"+gcBean.getName()+"'";
    }
    logger.log("Garbage collectors: "+gcList);
    logger.log("GC statistics period = "+statisticsPeriodLength + " sec");
  }

  /*
  * запись сообщения в лог
  * */
//  private void log(String message) {
//    System.out.println(message);
//
//    try {
//      Files.write(Paths.get("/Users/prologistic/files.txt"), data.getBytes());
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }

  /*
  * логирование информации о срабатывании GC
  * */
  private void logGCEventInfo(GarbageCollectionNotificationInfo gcInfo) {
    StringBuilder sb = new StringBuilder();
    sb.append(gcInfo.getGcName())
      .append(" -> ")
      .append(gcInfo.getGcInfo().getId())
      .append(" / ")
      .append(gcInfo.getGcAction())
      .append(" / ")
      .append(gcInfo.getGcCause())
      .append(" / ")
      .append(gcInfo.getGcInfo().getDuration())
      .append(" ms");
    logger.log(sb.toString());
  }

  /*
  * обновление статистики за указанный период времени
  *
  * String gcName - имя garbage collector
  * long duration - продолжительность работы garbage collector в миллисекундах
  * long periodNum - номер временного периода
  * */
  private void updateStatistics(String gcName, long duration, int periodNum) {
    Map<String, GCStatistic> m = (statistics.containsKey(periodNum)? statistics.get(periodNum) : new HashMap<String, GCStatistic>());

    GCStatistic gcStat = (m.containsKey(gcName)? m.get(gcName): new GCStatistic());

    gcStat.increaseCollectionCount();
    gcStat.addDuration(duration);

    m.put(gcName, gcStat);

    statistics.put(periodNum, m);
  }

  /*
  * возвращает номер периода статистики
  * */
  private int getStatisticsPeriodNum(long elapsedTime){
    int t = statisticsPeriodLength * 1000;
    return (int) elapsedTime / t + 1;
  }

  /*
  * сбор статистики по работе GS за указанный период
  * */
  private void saveStatisticsForPeriod(int periodNum) {
    Map<String, GCStatistic> st = statistics.get(periodNum);
    logger.log("Garbage collection statistics for period "+periodNum+":");

    Set<String> keys = st.keySet();

    for (String key : keys) {
      GCStatistic gcs = st.get(key);
      StringBuffer sb = new StringBuffer();
      sb.append("GS name = '")
          .append(key)
          .append("', Collections = ")
          .append(gcs.getCollectionCount())
          .append(", Total time spent = ")
          .append(gcs.getTotalTime()/1000).append(".").append(gcs.getTotalTime()%1000)
          .append(" sec");
      logger.log(sb.toString());
    }
  }

  /*
  * статистика по работе garbage collector
  * */
  private class GCStatistic {
    private long totalTime;
    private int collectionCount;

    public GCStatistic() {
      totalTime = 0;
      collectionCount = 0;
    }

    public long getTotalTime() {
      return totalTime;
    }

    public int getCollectionCount() {
      return collectionCount;
    }

    public void increaseCollectionCount() {
      collectionCount++;
    }

    public void addDuration(long duration) {
      totalTime += duration;
    }
  }

}
