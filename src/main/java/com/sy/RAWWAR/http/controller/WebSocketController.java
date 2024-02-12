package com.sy.RAWWAR.http.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.sy.RAWWAR.model.messages.LacsGatewayEventMessage;
import com.sy.RAWWAR.model.messages.RegisterMessage;
import com.sy.RAWWAR.model.messages.WebSocketMessage;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author Bianchi Gianluca
 * 
 *         Handles any web socket message from the LACS Gateway and from each
 *         React App client.
 */

@Controller
@Slf4j
public class WebSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate; // 1
    private final Set<String> connectedKits; // 2

    public WebSocketController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate; // 1
        connectedKits = new HashSet<>(); // 2
    }

    /**
     * 
     * @param message
     */
    @MessageMapping("/events") // 3
    public void processEvent(LacsGatewayEventMessage message) {

        if (message.getType() == null) {
            log.error("Il tipo di evento è nullo", new IllegalStateException());
        } else {
            System.out.println("eventoopoooooo!! " + message.getData().getMissionId());
        }

    }

    /**
     * 
     * @param webChatUsername
     * @return
     */
    @MessageMapping("/register") // 3
    @SendTo("/topic/room")
    public String registerUser(RegisterMessage message) {

        /*
         * connectedKits.add(webChatUsername);
         * System.out.println("ok registrato con: " + webChatUsername);
         * return "ok registrato con: " + webChatUsername;
         */
        return "";

    }

    /**
     * 
     * @param webChatUsername
     * @return
     */
    @MessageMapping("/unregister") // 5
    @SendTo("/topic/disconnectedUser")
    public String unregisterUser(String webChatUsername) {
        connectedKits.remove(webChatUsername);
        return webChatUsername;
    }

    /**
     * 
     * @param message
     */
    @MessageMapping("/message") // 6
    public void greeting(WebSocketMessage message) {
        if (this.connectedKits.contains((String) message.toWhom)) {
            simpMessagingTemplate.convertAndSendToUser(message.toWhom, "/msg", message);
        } else {

        }

    }
}
