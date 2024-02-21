package com.sy.RAWWAR.ws;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import com.sy.RAWWAR.repository.SocketRepository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WebSocketControllerTest {
    // STATIC
    private static final String MISSION_ID = "01";
    private static final String KIT_ID = "kit10";
    private static final String KIT_PASSCODE = "passcode";
    private static final String LACS_SAMPLE_MESSAGE = "{\"missionId\":\"01\"}";
    private static final String REACT_APP_WS_URI = "ws://localhost:3000/api/kits/";
    private static final String LACS_GW_WS_URI = "ws://localhost:3000/api/events/";
    // NON STATIC
    private WebSocketSession wsReact;
    private WebSocketSession wsLacs;
    private LacsWebSocketHandler wsLacsHandler;
    private ReactWebSocketHandler wsReactHandler;

    @Autowired
    private SocketRepository missionRepo;

    @BeforeEach
    void setup() throws InterruptedException {

        var wsClientLacs = new StandardWebSocketClient();
        var wsClientReact = new StandardWebSocketClient();
        this.wsLacsHandler = new LacsWebSocketHandler();
        this.wsReactHandler = new ReactWebSocketHandler();

        var reactSession = wsClientReact.execute(this.wsReactHandler, new WebSocketHttpHeaders(),
                URI.create(WebSocketControllerTest.REACT_APP_WS_URI + WebSocketControllerTest.MISSION_ID + "/"
                        + WebSocketControllerTest.KIT_ID + "/" + WebSocketControllerTest.KIT_PASSCODE));

        var lacsSession = wsClientLacs.execute(this.wsLacsHandler, new WebSocketHttpHeaders(),
                URI.create(WebSocketControllerTest.LACS_GW_WS_URI + WebSocketControllerTest.MISSION_ID));

        this.wsLacs = lacsSession.join();
        this.wsReact = reactSession.join();
        Thread.sleep(2000);
        assertTrue(this.wsLacs.isOpen() && this.wsReact.isOpen());
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void testEventPublishing() throws InterruptedException {
        TextMessage message = new TextMessage(WebSocketControllerTest.LACS_SAMPLE_MESSAGE);
        assertDoesNotThrow(() -> this.wsLacs.sendMessage(message));
        Thread.sleep(2000);
        assertTrue(this.wsReactHandler.getLastMessage().isPresent());
        assertTrue(this.wsReactHandler.getLastMessage().get().equals(WebSocketControllerTest.LACS_SAMPLE_MESSAGE));
    }

    public class LacsWebSocketHandler implements WebSocketHandler {
        private Optional<String> lastMessage;

        public LacsWebSocketHandler() {
            this.lastMessage = Optional.empty();
        }

        public Optional<String> getLastMessage() {
            return this.lastMessage;
        }

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        }

        @Override
        public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
            var content = (String) message.getPayload();
            this.lastMessage = Optional.of(content);
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

    }

    public class ReactWebSocketHandler implements WebSocketHandler {

        private Optional<String> lastMessage;

        public ReactWebSocketHandler() {
            this.lastMessage = Optional.empty();
        }

        public Optional<String> getLastMessage() {
            return this.lastMessage;
        }

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        }

        @Override
        public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
            var content = (String) message.getPayload();
            this.lastMessage = Optional.of(content);
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

    }

}
