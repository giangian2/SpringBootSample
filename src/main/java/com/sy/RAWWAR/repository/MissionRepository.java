package com.sy.RAWWAR.repository;

import com.sy.RAWWAR.model.mission.*;

import lombok.Getter;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.websocket.Session;
import java.util.ArrayList;
import java.util.Optional;

@Component
@Getter
public class MissionRepository {
    private List<Mission> missions;

    public MissionRepository() {
        this.missions = new ArrayList<>();
    }

    public void add(Mission mission) {
        this.missions.add(mission);
    }

    public Optional<Mission> findByMissionId(String missionId) {
        return this.getMissions()
                .stream()
                .filter(el -> el.getMissionId().equals(missionId))
                .findFirst();
    }

}
