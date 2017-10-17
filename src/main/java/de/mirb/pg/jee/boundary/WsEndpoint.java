package de.mirb.pg.jee.boundary;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ServerEndpoint("/action")
public class WsEndpoint {
  private Session session;

  @OnOpen
  public void open(Session session) {
    this.session = session;
  }

  @OnClose
  public void close(Session session) {
    this.session = null;
  }

  @OnError
  public void onError(Throwable error) {
  }

  @OnMessage
  public void handleMessage(String message, Session session) {
    change(message);
  }

  public void change(String message) {
    if (this.session != null && this.session.isOpen()) {
      SimpleDateFormat sdf = new SimpleDateFormat("HH:MM:ss.SSS");
      try {
//        JsonObject event = Json.createObjectBuilder().
//          add("name","").build();
//        this.session.getBasicRemote().sendObject(event);

        this.session.getBasicRemote().sendObject("Received [" + message
          + "] at " + sdf.format(new Date()));
      } catch (IOException ex) {
        //we ignore this
      } catch (EncodeException e) {
        e.printStackTrace();
      }
    }
  }
}
