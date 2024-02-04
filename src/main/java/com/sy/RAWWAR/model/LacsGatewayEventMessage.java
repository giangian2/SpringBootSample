package com.sy.RAWWAR.model;

import com.sy.RAWWAR.enums.LacsEventType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LacsGatewayEventMessage {
    private final String timestamp;
    private final String type;
    private final LacsGatewayDataMessage data;
}
