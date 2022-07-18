package com.example.tradingapp.websocket;


import com.example.tradingapp.websocket.encoder.LoginRequestEncoder;
import com.example.tradingapp.websocket.model.request.LoginRequest;
import com.example.tradingapp.websocket.model.request.SubscribeArg;
import com.example.tradingapp.websocket.model.request.SubscribeRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class MyWebsocketHandler implements WebSocketHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("afterConnectionEstablished. session: {}", session);
        LoginRequest login = new LoginRequestEncoder().encode();
        String loginJson = mapper.writeValueAsString(login);

        log.info("Sending login: {}", loginJson);
        session.sendMessage(new TextMessage(loginJson));

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.info("handleMessage. session: {}. message: {}", session, message);
        log.info("Payload {}", message.getPayload());

        JsonNode jsonNode = mapper.readTree(message.getPayload().toString());

        if (jsonNode.has("event") && "login".equals(jsonNode.get("event").asText())) {

            if ("0".equals(jsonNode.get("code").asText())) {
                var subscribeArg = SubscribeArg.builder()
                        .instType("ANY")
                        .channel("orders")
                        .build();
                List<SubscribeArg> args = new ArrayList<>();
                args.add(subscribeArg);
                var subscribeRequest = SubscribeRequest.builder()
                        .op("subscribe")
                        .args(args)
                        .build();
                String subscribeJson = mapper.writeValueAsString(subscribeRequest);
                session.sendMessage(new TextMessage(subscribeJson));
            } else {
                log.warn("Login not successful");
            }

        } else if (jsonNode.has("event") && "subscribe".equals(jsonNode.get("event").asText())) {

            log.info("Subscribed to " + jsonNode.get("arg").get("channel").asText());

        }

    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("handleTransportError. session: {}", session, exception);

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("afterConnectionClosed. session: {}. closeStatus: {}", session, closeStatus);

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}