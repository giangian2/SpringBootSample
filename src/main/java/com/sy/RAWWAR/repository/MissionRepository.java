package com.sy.RAWWAR.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sy.RAWWAR.dto.MissionDto;

@Repository
public interface MissionRepository extends MongoRepository<MissionDto, String> {

}
