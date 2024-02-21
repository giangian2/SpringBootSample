package com.sy.RAWWAR.model.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LacsGatewayDataMessage {
    private String missionId;
    private String missionStatus;
    private String kitId;
    private String sourceId;
    private String hitZone;
    private int ammoType;
    private boolean isKilled;
}
