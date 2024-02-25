package com.sy.RAWWAR.http.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sy.RAWWAR.configuration.ApplicationContextProvider;
import com.sy.RAWWAR.dto.MissionDto;
import com.sy.RAWWAR.model.mission.Mission;
import com.sy.RAWWAR.repository.SocketRepository;
import com.sy.RAWWAR.repository.MissionRepository;
import java.util.ArrayList;

@Controller
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private MissionRepository missionRepo;

    @PostMapping()
    public ResponseEntity<String> getWebSocketUrl() {
        missionRepo.save(new MissionDto("01", "started", new ArrayList<String>(), null, null));
        return ResponseEntity.ok().body("http://localhost:3000/api/server/" + "01");
    }

}
