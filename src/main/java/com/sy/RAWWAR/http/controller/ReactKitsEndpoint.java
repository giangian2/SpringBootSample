package com.sy.RAWWAR.http.controller;

import org.springframework.stereotype.Component;

import com.sy.RAWWAR.configuration.ApplicationContextProvider;
import com.sy.RAWWAR.repository.MissionRepository;

import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.HashMap;

@Component
@ServerEndpoint(value = "/kits/{missionId}/{kitId}/{passcode}")
public class ReactKitsEndpoint {
    private MissionRepository repo = ApplicationContextProvider.getApplicationContext()
            .getBean(MissionRepository.class);

    @OnOpen
    public void open(Session session, @PathParam("kitId") String kitId, @PathParam("missionId") String missionId,
            @PathParam("passcode") String passcode) {

        var mission = this.repo.findByMissionId(missionId);
        if (mission.isPresent()) {
            mission.get().addKit(kitId, session);
            System.out.println(kitId);
        }
    }
}
