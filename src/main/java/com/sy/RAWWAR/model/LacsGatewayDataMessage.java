package com.sy.RAWWAR.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LacsGatewayDataMessage {
    private final String missionId;
    private final String missionStatus;
    private final String kitId;
    private final String sourceId;
    private final String hitZone;
    private final int ammoType;
    private final boolean isKilled;
}
