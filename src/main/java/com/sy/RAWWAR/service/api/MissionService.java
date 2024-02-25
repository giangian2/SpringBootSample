package com.sy.RAWWAR.service.api;

import java.util.Optional;

import com.sy.RAWWAR.dto.LacsGatewayEventMessage;
import com.sy.RAWWAR.dto.MissionDto;

import java.util.List;

public interface MissionService {
    Optional<MissionDto> findById(String missionId);

    List<LacsGatewayEventMessage> getMissionEvents(MissionDto mission);

    void insert(MissionDto mission);

    void save(MissionDto mission);

    void addPlayer(String missionId, String kitId);

    void alterStatus(String missionId, String status);
}
