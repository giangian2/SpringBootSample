package com.sy.RAWWAR.http.controller;

import org.springframework.stereotype.Component;

import com.sy.RAWWAR.configuration.ApplicationContextProvider;
import com.sy.RAWWAR.model.messages.decoder.LacsGatewayEventMessageDecoder;
import com.sy.RAWWAR.model.messages.encoder.LacsGatewayEventMessageEncoder;
import com.sy.RAWWAR.repository.MissionRepository;
import com.sy.RAWWAR.repository.SocketRepository;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.HashMap;

@Component
@ServerEndpoint(value = "/kits/{missionId}/{kitId}", encoders = {
        LacsGatewayEventMessageEncoder.class }, decoders = {
                LacsGatewayEventMessageDecoder.class })
public class ReactKitsEndpoint {
    private SocketRepository repo = ApplicationContextProvider.getApplicationContext()
            .getBean(SocketRepository.class);

    private MissionRepository missionRepo = ApplicationContextProvider.getApplicationContext()
            .getBean(MissionRepository.class);

    @OnOpen
    public void open(Session session, @PathParam("kitId") String kitId, @PathParam("missionId") String missionId) {

        var mission = this.repo.findByMissionId(missionId);
        if (mission.isPresent()) {
            mission.get().addKit(kitId, session);
            var updatedMission = this.missionRepo.findById(missionId).get();
            updatedMission.getPlayers().add(kitId);
            this.missionRepo.save(updatedMission);
            System.out.println(kitId);
        }
    }

    @OnClose
    public void close(Session session, @PathParam("kitId") String kitId, @PathParam("missionId") String missionId) {
        this.repo.removeKit(missionId, kitId);
    }

    @OnError
    public void onError(Throwable t) {
        System.out.println(t.getMessage());
    }
}
