package com.sy.RAWWAR.repository;

import com.sy.RAWWAR.model.mission.*;

import lombok.Getter;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.websocket.Session;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collector;

public class SocketRepository {
    private List<Mission> missions = new ArrayList<>();

    public SocketRepository() {

    }

    public void add(Mission mission) {
        this.missions.add(mission);
    }

    public Optional<Mission> findByMissionId(String missionId) {
        return this.missions.stream().filter(m -> m.getMissionId().equals(missionId)).findFirst();
    }

    public void removeMission(String missionId) {
        if (!this.findByMissionId(missionId).isEmpty()) {
            this.missions.removeIf((m) -> m.getMissionId().equals(missionId));
        }
    }

    public void removeKit(String missionId, String kitId) {
        if (!this.findByMissionId(missionId).isEmpty()) {
            this.findByMissionId(missionId).get().getKits().remove(kitId);
        }
    }

}
