package com.sy.RAWWAR.model.mission;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Mission {
    private List<Kit> kits;
    private String missionId;
    private String missionStatus;

    public void updateMissionStatus(String newStatus) {
        this.missionStatus = newStatus;
    }

    public void addKit(Kit kit) {
        this.kits.add(kit);
    }

    public void addKits(List<Kit> list) {
        this.kits.addAll(list);
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
