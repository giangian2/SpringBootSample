package com.sy.RAWWAR.model.messages.encoder;

import org.json.JSONObject;

import com.sy.RAWWAR.dto.LacsGatewayEventMessage;

import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;

public class LacsGatewayEventMessageEncoder implements Encoder.Text<LacsGatewayEventMessage> {

    @Override
    public String encode(LacsGatewayEventMessage object) throws EncodeException {
        return new JSONObject(object).toString();
    }

}
