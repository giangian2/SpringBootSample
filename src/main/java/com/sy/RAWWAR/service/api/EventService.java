package com.sy.RAWWAR.service.api;

import java.util.Optional;

import com.sy.RAWWAR.dto.LacsGatewayEventMessage;

import java.util.List;

public interface EventService {
    Optional<LacsGatewayEventMessage> findById(String id);

    List<LacsGatewayEventMessage> findByMissionId(String missionId);
}
