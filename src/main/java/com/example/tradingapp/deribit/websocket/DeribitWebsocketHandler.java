package com.example.tradingapp.deribit.websocket;

import com.example.tradingapp.deribit.websocket.model.request.DeribitRequest;
import com.example.tradingapp.deribit.websocket.model.request.DeribitRequestParamsLogin;
import com.example.tradingapp.positions.Positions;
import com.example.tradingapp.secrets.DeribitSecrets;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeribitWebsocketHandler implements WebSocketHandler {

    private final DeribitLoginStateWS loginStateWS;
    private final Positions positions;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("afterConnectionEstablished: {}", session);
        login(session);
    }

    private void login(WebSocketSession session) throws IOException {
        var loginParams = DeribitRequestParamsLogin.builder()
                .grantType("client_credentials")
                .clientId(DeribitSecrets.CLIENT_ID)
                .clientSecret(DeribitSecrets.CLIENT_SECRET)
                .build();
        var deribitRequest = DeribitRequest.builder()
                .id("login")
                .method("public/auth")
                .params(loginParams)
                .build();
        String loginJson = mapper.writeValueAsString(deribitRequest);
        log.info("Sending loginRequest to Deribit");
        session.sendMessage(new TextMessage(loginJson));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.info("handleMessage: session-{},message-{}", session, message.getPayload());
        JsonNode messageJsonNode = mapper.readTree(message.getPayload().toString());

        if (messageJsonNode.has("error")) {
            log.error("Deribit WebSocket error: {}", messageJsonNode.get("error").asText());
        } else if (messageJsonNode.has("id") && "login".equals(messageJsonNode.get("id").asText())) {
            log.info("Logged in Deribit WebSocket");
            loginStateWS.setLoggedIn(true);
            loginStateWS.setSession(session);
        } else if (messageJsonNode.has("id") && "positions".equals(messageJsonNode.get("id").asText())) {
            log.info("Received Positions");
            positions.updatePositionsDeribit(messageJsonNode);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.warn("handleTransportError: session-{}", session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("afterConnectionEstablished: session-{},closeStatus-{}", session, closeStatus);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
