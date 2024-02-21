package com.sy.RAWWAR.model.messages.decoder;

import java.io.StringReader;
import java.util.Optional;

import org.json.JSONObject;

import com.sy.RAWWAR.dto.LacsGatewayEventMessage;
import com.sy.RAWWAR.model.messages.LacsGatewayDataMessage;

import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;

public class LacsGatewayEventMessageDecoder implements Decoder.Text<LacsGatewayEventMessage> {

    @Override
    public LacsGatewayEventMessage decode(String jsonMessage) throws DecodeException {
        Optional<LacsGatewayDataMessage> data = Optional.empty();
        String missionId;
        String missionStatus;
        String kitId;
        String sourceId;
        String hitZone;
        int ammoType;
        boolean isKilled;

        var jsObject = new JSONObject(jsonMessage);
        var type = jsObject.optString("type");
        var dataObject = new JSONObject(jsObject.optString("data"));

        if (!type.isEmpty()) {
            switch (type) {
                case "missionStatusEvent":

                    missionId = dataObject.optString("missionId");
                    missionStatus = dataObject.optString("missionStatus");
                    if (missionId.isEmpty() || missionStatus.isEmpty()) {
                        throw new IllegalStateException(
                                "missionId and missionStatus cannot be null for missionStatusEvent");
                    }
                    data = Optional.of(new LacsGatewayDataMessage(missionId, missionStatus, "", "", "", 0, false));
                    break;

                case "hitEvent":
                    kitId = dataObject.optString("kitId");
                    sourceId = dataObject.optString("sourceId");
                    hitZone = dataObject.optString("hitZone");
                    ammoType = dataObject.optInt("ammoType");
                    isKilled = dataObject.optBoolean("isKilled");
                    if (kitId.isEmpty() || sourceId.isEmpty() || hitZone.isEmpty()) {
                        throw new IllegalStateException("KitId, sourceId and hitZone cannot be null for hitEvent");
                    }
                    data = Optional
                            .of(new LacsGatewayDataMessage("", "", kitId, sourceId, hitZone, ammoType, isKilled));
                    break;

                case "reviveEvent":
                    kitId = dataObject.optString("kitId");
                    sourceId = dataObject.optString("sourceId");
                    if (kitId.isEmpty() || sourceId.isEmpty()) {
                        throw new IllegalStateException("KitId and sourceId cannot be null for reviveEvent");
                    }
                    data = Optional.of(new LacsGatewayDataMessage("", "", kitId, sourceId, "", 0, false));
                    break;

                default:
                    throw new IllegalStateException("JSON message does not have valid type, type received: " + type);
            }
        } else {
            throw new IllegalStateException("JSON message does not have a type");
        }

        if (data.isEmpty()) {
            throw new IllegalStateException("JSON message does not have a data attribute");
        }

        return new LacsGatewayEventMessage(jsObject.getString("timestamp"), jsObject.getString("type"),
                data.get());

    }

    @Override
    public boolean willDecode(String jsonMessage) {
        try {
            // Check if incoming message is valid JSON
            JSONObject jsonObj = new JSONObject(jsonMessage);
            if (jsonObj.isEmpty()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
