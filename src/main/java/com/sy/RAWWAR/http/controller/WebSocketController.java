package com.sy.RAWWAR.http.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.sy.RAWWAR.model.LacsGatewayEventMessage;
import com.sy.RAWWAR.model.WebSocketMessage;

@Controller
public class WebSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate; // 1
    private final Set<String> connectedUsers; // 2

    public WebSocketController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate; // 1
        connectedUsers = new HashSet<>(); // 2
    }

    @MessageMapping("/events") // 3
    public void processEvent(LacsGatewayEventMessage message) {
        System.out.println("eventoopoooooo!! " + message.getData().getMissionId());

    }

    @MessageMapping("/register") // 3
    @SendTo("/topic/room")
    public String registerUser(String webChatUsername) {

        connectedUsers.add(webChatUsername);

        System.out.println("ok registrato con: " + webChatUsername);
        return "ok registrato con: " + webChatUsername;

    }

    @MessageMapping("/unregister") // 5
    @SendTo("/topic/disconnectedUser")
    public String unregisterUser(String webChatUsername) {
        connectedUsers.remove(webChatUsername);
        return webChatUsername;
    }

    @MessageMapping("/message") // 6
    public void greeting(WebSocketMessage message) {
        simpMessagingTemplate.convertAndSendToUser(message.toWhom, "/msg", message);
    }
}
