package com.sy.RAWWAR.ws;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Console;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.sy.RAWWAR.model.mission.Mission;
import com.sy.RAWWAR.repository.MissionRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MissionRepositoryTest {

    @Autowired
    private MissionRepository missionRepo;

    @Test
    public void contextLoads() {

    }

    @Test
    public void insert() {
        this.missionRepo.add(new Mission("01", "START", null));
        assertTrue(this.missionRepo.findByMissionId("01").isPresent());
    }

    @Test
    public void testWS() throws InterruptedException, IOException {

        WebSocketClient wsClientLacs = new StandardWebSocketClient();

        WebSocketClient wsClientReact = new StandardWebSocketClient();

        var reactSession = wsClientReact.execute(new WebSocketHandler() {

            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            }

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
                var content = (String) message.getPayload();
                System.out.println("React: " + content);
            }

            @Override
            public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

            }

            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

            }

            @Override
            public boolean supportsPartialMessages() {
                return true;
            }

        }, new WebSocketHttpHeaders(), URI.create("ws://localhost:3000/api/kits/01/kit01/abcd"));

        var lacsSession = wsClientLacs.execute(new WebSocketHandler() {

            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {

            }

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
                var content = (String) message.getPayload();
                System.out.println("LACS: " + content);
            }

            @Override
            public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

            }

            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

            }

            @Override
            public boolean supportsPartialMessages() {
                return true;
            }

        }, new WebSocketHttpHeaders(), URI.create("ws://localhost:3000/api/events/01"));

        Thread.sleep(2000);
        var wsLacs = lacsSession.join();
        var wsReact = reactSession.join();
        TextMessage message = new TextMessage("{\"missionId\":\"01\"}");

        wsLacs.sendMessage(message);
        Thread.sleep(2000);

        try {
            wsLacs.close();
            wsReact.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
