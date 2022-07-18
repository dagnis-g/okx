package com.example.tradingapp.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.net.URI;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyWebsocketClient {

    private final MyWebsocketHandler myWebsocketHandler;
    private static String URL = "wss://wspap.okx.com:8443/ws/v5/private?brokerId=9999";

    public void startWebSocketConnection() {
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        headers.add("x-simulated-trading", "1");
        client.doHandshake(myWebsocketHandler, headers, URI.create(URL));

        log.info("Connecting");
    }
}
