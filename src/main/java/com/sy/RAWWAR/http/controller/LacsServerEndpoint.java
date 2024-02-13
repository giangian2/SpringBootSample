package com.sy.RAWWAR.http.controller;

import java.io.IOException;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import com.sy.RAWWAR.configuration.ApplicationContextProvider;
import com.sy.RAWWAR.model.messages.RegisterMessage;
import com.sy.RAWWAR.model.mission.Mission;
import com.sy.RAWWAR.repository.MissionRepository;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

@Component
@ServerEndpoint(value = "/events/{missionId}")
public class LacsServerEndpoint {
    private MissionRepository repo = ApplicationContextProvider.getApplicationContext()
            .getBean(MissionRepository.class);

    @OnOpen
    public void open(Session session, @PathParam("missionId") String missionId) {
        var mission = this.repo.findByMissionId(missionId);
        if (mission.isPresent()) {
            mission.get().setGateway(session);
        } else {
            // errore
        }
    }

    @OnMessage
    public String handleMessage(String message, Session session, @PathParam("missionId") String missionId) {
        try {
            var jsObject = new JSONObject(message);
            session.getBasicRemote().sendText(jsObject.getString("missionId"));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "message_received";
    }

    @OnClose
    public void close(Session session) {

    }

    @OnError
    public void onError(Throwable t) {
        System.out.println(t.getMessage());
    }

}