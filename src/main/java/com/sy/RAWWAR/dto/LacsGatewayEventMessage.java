package com.sy.RAWWAR.dto;

import org.springframework.data.mongodb.core.mapping.Document;

import com.sy.RAWWAR.enums.LacsEventType;
import com.sy.RAWWAR.model.messages.LacsGatewayDataMessage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Document(collection = "events")
@Getter
@Setter
public class LacsGatewayEventMessage {
    private final String timestamp;
    private final String type;
    private final LacsGatewayDataMessage data;
}
