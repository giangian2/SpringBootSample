package com.sy.RAWWAR.http.controller;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.sy.RAWWAR.configuration.ApplicationContextProvider;
import com.sy.RAWWAR.model.messages.LacsGatewayEventMessage;
import com.sy.RAWWAR.model.messages.decoder.LacsGatewayEventMessageDecoder;
import com.sy.RAWWAR.model.messages.encoder.LacsGatewayEventMessageEncoder;
import com.sy.RAWWAR.repository.MissionRepository;

import jakarta.websocket.EncodeException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import org.json.JSONException;
import org.json.JSONObject;

@Component
@ServerEndpoint(value = "/events/{missionId}", encoders = { LacsGatewayEventMessageEncoder.class }, decoders = {
        LacsGatewayEventMessageDecoder.class })

public class LacsServerEndpoint {
    private MissionRepository repo = ApplicationContextProvider.getApplicationContext()
            .getBean(MissionRepository.class);

    @OnOpen
    public void open(Session session, @PathParam("missionId") String missionId) {
        var mission = this.repo.findByMissionId(missionId);
        if (mission.isPresent()) {
            mission.get().setGateway(session);
            System.out.println("Gateway setted");
        } else {
            // errore
        }
    }

    @OnMessage
    public String handleMessage(LacsGatewayEventMessage message, Session session,
            @PathParam("missionId") String missionId) {
        try {
            var mission = this.repo.findByMissionId(missionId);
            if (mission.isPresent()) {
                mission.get().getKits().values().forEach((k) -> {
                    try {
                        System.out.println("Forwarding event");
                        k.getBasicRemote().sendObject(message);
                    } catch (IOException | EncodeException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                });
            }
        } catch (JSONException e) {
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