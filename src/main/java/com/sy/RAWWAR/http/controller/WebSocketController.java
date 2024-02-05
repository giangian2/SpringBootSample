package com.sy.RAWWAR.http.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.sy.RAWWAR.model.LacsGatewayEventMessage;
import com.sy.RAWWAR.model.WebSocketMessage;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class WebSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate; // 1
    private final Set<String> connectedKits; // 2

    public WebSocketController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate; // 1
        connectedKits = new HashSet<>(); // 2
    }

    @MessageMapping("/events") // 3
    public void processEvent(LacsGatewayEventMessage message) {

        if (message.getType() == null) {
            log.error("Il tipo di evento è nullo", new IllegalStateException());
        }
        System.out.println("eventoopoooooo!! " + message.getData().getMissionId());

    }

    @MessageMapping("/register") // 3
    @SendTo("/topic/room")
    public String registerUser(String webChatUsername) {

        connectedKits.add(webChatUsername);
        System.out.println("ok registrato con: " + webChatUsername);
        return "ok registrato con: " + webChatUsername;

    }

    @MessageMapping("/unregister") // 5
    @SendTo("/topic/disconnectedUser")
    public String unregisterUser(String webChatUsername) {
        connectedKits.remove(webChatUsername);
        return webChatUsername;
    }

    @MessageMapping("/message") // 6
    public void greeting(WebSocketMessage message) {
        simpMessagingTemplate.convertAndSendToUser(message.toWhom, "/msg", message);
    }
}
