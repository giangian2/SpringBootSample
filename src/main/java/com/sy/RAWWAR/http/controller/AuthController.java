package com.sy.RAWWAR.http.controller;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("auth")
public class AuthController {

    @PostMapping()
    public ResponseEntity<String> getWebSocketUrl() {
        return ResponseEntity.ok().body("testurl");
    }

}
