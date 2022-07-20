package com.example.tradingapp.websocket;


import com.example.tradingapp.websocket.encoder.LoginRequestEncoder;
import com.example.tradingapp.websocket.model.request.LoginRequest;
import com.example.tradingapp.websocket.model.request.SubscribeArg;
import com.example.tradingapp.websocket.model.request.SubscribeRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class OkxWebsocketHandler implements WebSocketHandler {

    private final OrderChannelHandler orderChannelHandler;
    private final AccountChannelHandler accountChannelHandler;
    private final ObjectMapper mapper = new ObjectMapper();
    @Value("${okx.symbols.enabled}")
    private String currencies;
    @Value("${okx.subscribe.channels}")
    private String channelsToSubscribeTo;

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
        log.info("payload {}", message.getPayload());

        JsonNode jsonNode = mapper.readTree(message.getPayload().toString());

        if (jsonNode.has("event") && "login".equals(jsonNode.get("event").asText())) {

            if ("0".equals(jsonNode.get("code").asText())) {
                if (!channelsToSubscribeTo.isBlank()) {
                    subscribeToChannels(session, channelsToSubscribeTo.split(","));
                }
            } else {
                log.warn("Login not successful: {}", jsonNode);
            }

        } else if (jsonNode.has("event") && "subscribe".equals(jsonNode.get("event").asText())) {

            log.info("Subscribed to " + jsonNode.get("arg").get("channel").asText());

        }

        if (isOrderFromOrderChannel(jsonNode)) {
            orderChannelHandler.handleOrderStatus(jsonNode);
        } else if (isChannelAccount(jsonNode)) {
            accountChannelHandler.handleBalance(jsonNode);
        }

    }

    private boolean isChannelAccount(JsonNode jsonNode) {
        return jsonNode.has("arg")
                && jsonNode.has("data")
                && jsonNode.get("arg").has("channel")
                && "account".equals(jsonNode.get("arg").get("channel").asText());
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

    private boolean isOrderFromOrderChannel(JsonNode jsonNode) {
        return jsonNode.has("arg")
                && jsonNode.has("data")
                && jsonNode.get("arg").has("channel")
                && "orders".equals(jsonNode.get("arg").get("channel").asText());
    }

    private void subscribeToChannels(WebSocketSession session, String[] channel) throws IOException {
        List<SubscribeArg> args = new ArrayList<>();

        for (var ch : channel) {
            if (ch.equals("account")) {
                if (!currencies.isBlank()) {
                    for (var ccy : currencies.split(",")) {
                        args.add(SubscribeArg.builder()
                                .instType("ANY")
                                .channel(ch)
                                .ccy(ccy)
                                .build());
                    }
                } else {
                    args.add(SubscribeArg.builder()
                            .instType("ANY")
                            .channel(ch)
                            .build());
                }

            } else {
                args.add(SubscribeArg.builder()
                        .instType("ANY")
                        .channel(ch)
                        .build());
            }
        }

        var subscribeRequest = SubscribeRequest.builder()
                .op("subscribe")
                .args(args)
                .build();
        String subscribeJson = mapper.writeValueAsString(subscribeRequest);
        session.sendMessage(new TextMessage(subscribeJson));
    }
}
