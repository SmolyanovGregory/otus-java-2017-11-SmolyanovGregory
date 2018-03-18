package ru.otus.smolyanov.websocket;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 15 (message system)
 */

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import ru.otus.smolyanov.cacheservice.CacheService;
import ru.otus.smolyanov.datasetgenerator.UserDataSetGenerator;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("UnusedDeclaration")
@WebSocket
public class AdmWebSocket {

  private static final String RUN_PROCESS_MSG = "runProcess";
  private static final String REFRESH_MSG = "refresh";

  private Session session;
  private final Gson gson = new Gson();
  private final Map<String, Object> cacheInfo = new HashMap<>();

  CacheService cacheService;
  UserDataSetGenerator randomUserGenerator;

  public AdmWebSocket(CacheService cacheService, UserDataSetGenerator randomUserGenerator) {
    this.cacheService = cacheService;
    this.randomUserGenerator = randomUserGenerator;
  }

  @OnWebSocketConnect
  public void onOpen(Session session) {
    this.session = session;
    processMsg(REFRESH_MSG, null);
  }

  @OnWebSocketMessage
  public void onMessage(String data) {
    JsonObject jsonObject = gson.fromJson(data, JsonObject.class);
    String key = jsonObject.get("key").getAsString();
    String value = jsonObject.get("value").getAsString();

    processMsg(key, value);
  }

  @OnWebSocketClose
  public void onClose(int statusCode, String reason) {
  }

  public void sendString(String data) {
    try {
      session.getRemote().sendString(data);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  private void processMsg(String msgType, String msgValue) {

    switch (msgType) {
      case RUN_PROCESS_MSG :
        if (Boolean.valueOf(msgValue)) {
          randomUserGenerator.startGenerating();
        } else {
          randomUserGenerator.stopGenerating();
        }
        break;

      case REFRESH_MSG :
        cacheInfo.put("cacheSize", cacheService.getSize());
        cacheInfo.put("hitCount", cacheService.getHitCount());
        cacheInfo.put("missCount", cacheService.getMissCount());
        cacheInfo.put("lifeTimeMs", cacheService.getLifeTimeMs());
        cacheInfo.put("idleTimeMs", cacheService.getIdleTimeMs());
        cacheInfo.put("isEternal", cacheService.getIsEternal());

        String msg = gson.toJson(cacheInfo);
        sendString(msg);
        break;
    }
  }
}
