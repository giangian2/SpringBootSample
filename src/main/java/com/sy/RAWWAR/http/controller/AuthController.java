package com.sy.RAWWAR.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sy.RAWWAR.configuration.ApplicationContextProvider;
import com.sy.RAWWAR.model.mission.Mission;
import com.sy.RAWWAR.repository.MissionRepository;;

@Controller
@RequestMapping("auth")
public class AuthController {

    private MissionRepository repo = ApplicationContextProvider.getApplicationContext()
            .getBean(MissionRepository.class);

    @PostMapping()
    public ResponseEntity<String> getWebSocketUrl() {
        this.repo.add(new Mission("01", "started", null));

        return ResponseEntity.ok().body("http://localhost:3000/api/server/" + "01");
    }

}
