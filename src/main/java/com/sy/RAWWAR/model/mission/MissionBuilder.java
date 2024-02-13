package com.sy.RAWWAR.model.mission;

import java.util.ArrayList;
import java.util.List;

public class MissionBuilder {

    private List<Kit> kits;
    private String missionId;
    private String missionStatus;

    public MissionBuilder() {
        this.kits = new ArrayList<>();
    }

    public MissionBuilder setMissionId(String mId) {
        this.missionId = mId;
        return this;
    }

    public MissionBuilder setMissionStatus(String mStatus) {
        this.missionStatus = mStatus;
        return this;
    }

    public MissionBuilder addKit(Kit kit) {
        this.kits.add(kit);
        return this;
    }

    public MissionBuilder addKits(List<Kit> kitList) {
        this.kits.addAll(kitList);
        return this;
    }

    public Mission build() {
        return new Mission(this.kits, this.missionId, this.missionStatus);
    }

}
