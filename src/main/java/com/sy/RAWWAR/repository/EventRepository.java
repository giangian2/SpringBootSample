package com.sy.RAWWAR.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sy.RAWWAR.dto.LacsGatewayEventMessage;

@Repository
public interface EventRepository extends MongoRepository<LacsGatewayEventMessage, String> {

}
