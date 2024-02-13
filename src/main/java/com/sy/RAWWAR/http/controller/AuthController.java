package com.sy.RAWWAR.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sy.RAWWAR.model.mission.Mission;
import com.sy.RAWWAR.repository.MissionRepository;

import net.bytebuddy.utility.RandomString;

@Controller
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private MissionRepository repo;

    @PostMapping()
    public ResponseEntity<String> getWebSocketUrl() {
        String generatedString = RandomString.make(10);
        this.repo.add(new Mission(generatedString, "started", null));
        return ResponseEntity.ok().body("http://localhost:3000/api/server/" + generatedString);
    }

}
