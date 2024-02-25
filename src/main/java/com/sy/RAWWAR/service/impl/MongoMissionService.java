package com.sy.RAWWAR.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sy.RAWWAR.configuration.ApplicationContextProvider;
import com.sy.RAWWAR.dto.LacsGatewayEventMessage;
import com.sy.RAWWAR.dto.MissionDto;
import com.sy.RAWWAR.repository.MissionRepository;
import com.sy.RAWWAR.service.api.MissionService;

@Service
public class MongoMissionService implements MissionService {
    private MissionRepository missionRepo = ApplicationContextProvider.getApplicationContext()
            .getBean(MissionRepository.class);

    @Override
    public Optional<MissionDto> findById(String missionId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<LacsGatewayEventMessage> getMissionEvents(MissionDto mission) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMissionEvents'");
    }

    @Override
    public void insert(MissionDto mission) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public void save(MissionDto mission) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public void addPlayer(String missionId, String kitId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addPlayer'");
    }

    @Override
    public void alterStatus(String missionId, String status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'alterStatus'");
    }

}
