package com.sy.RAWWAR.repository;

import org.springframework.data.repository.CrudRepository;

import com.sy.RAWWAR.dto.Passcode;

public interface PasscodeRepository extends CrudRepository<Passcode, String> {
    Passcode findByKitId(String kitId);

}
