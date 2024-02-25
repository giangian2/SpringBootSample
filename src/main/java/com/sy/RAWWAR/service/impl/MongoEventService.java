package com.sy.RAWWAR.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sy.RAWWAR.configuration.ApplicationContextProvider;
import com.sy.RAWWAR.dto.LacsGatewayEventMessage;
import com.sy.RAWWAR.repository.EventRepository;
import com.sy.RAWWAR.service.api.EventService;

@Service
public class MongoEventService implements EventService {
    private EventRepository eventRepo = ApplicationContextProvider.getApplicationContext()
            .getBean(EventRepository.class);

    @Override
    public Optional<LacsGatewayEventMessage> findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<LacsGatewayEventMessage> findByMissionId(String missionId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByMissionId'");
    }

}
