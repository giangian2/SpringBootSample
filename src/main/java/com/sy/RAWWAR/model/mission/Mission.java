package com.sy.RAWWAR.model.mission;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.websocket.Session;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class Mission {
    private Map<String, Session> kits;
    private Session gateway;
    private String missionId;
    private String missionStatus;

    public Mission(String missionId, String missionStatus, Session gateway) {
        this.missionId = missionId;
        this.missionStatus = missionStatus;
        this.gateway = gateway;
        this.kits = new HashMap<>();
    }

    public void updateMissionStatus(String newStatus) {
        this.missionStatus = newStatus;
    }

    public void addKit(String kitId, Session wsSession) {
        this.kits.put(kitId, wsSession);
    }

    public void setGateway(Session gateway) {
        this.gateway = gateway;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Mission) {
            var mission = (Mission) o;
            if (this.missionId == mission.getMissionId()) {
                return true;
            }
        }
        return false;
    }
}
