package com.sy.RAWWAR.http.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.sy.RAWWAR.model.messages.LacsGatewayEventMessage;
import com.sy.RAWWAR.model.messages.RegisterMessage;
import com.sy.RAWWAR.model.messages.WebSocketMessage;
import com.sy.RAWWAR.model.mission.Kit;
import com.sy.RAWWAR.model.mission.Mission;
import com.sy.RAWWAR.model.mission.MissionBuilder;
import com.sy.RAWWAR.repository.PasscodeRepository;

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
    private final Map<String, Mission> missions;
    @Autowired
    private PasscodeRepository passcodeRepo;

    public WebSocketController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate; // 1
        connectedKits = new HashSet<>(); // 2
        missions = new HashMap<>();
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
            if (message.getType().equals("missionStatusEvent")) {
                System.out.println(message.getType());
                var mId = message.getData().getMissionId();
                var mStatus = message.getData().getMissionStatus();
                if (this.missions.keySet().contains(mId)) {
                    this.missions.get(mId).updateMissionStatus(mStatus);
                } else {
                    this.missions.put(mId, new MissionBuilder()
                            .setMissionId(mId)
                            .setMissionStatus(mStatus)
                            .build());
                }

                this.missions.keySet().forEach((k) -> System.out.println(k));
            }

            if (message.getType().equals("hitEvent")) {
                simpMessagingTemplate.convertAndSendToUser(message.getData().getKitId(), "/msg", message);

            }
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

        if (this.missions.keySet().contains(message.getMissionId())) {
            var passcode = this.passcodeRepo.findById(message.getKitId());
            System.out.println("Passcode received: " + message.getPasscode() + " Passcode from db: "
                    + passcode.get().getPasscode());
            if (passcode.isPresent()) {
                if (passcode.get().getPasscode().equals(message.getPasscode())) {

                    this.missions.get(message.getMissionId()).addKit(new Kit(message.getKitId()));
                    connectedKits.add(message.getKitId());
                }
            }
        }

        this.connectedKits.forEach((k) -> System.out.println(k));
        /*
         * 
         * System.out.println("ok registrato con: " + webChatUsername);
         * return "ok registrato con: " + webChatUsername;
         */
        return "ok";

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
