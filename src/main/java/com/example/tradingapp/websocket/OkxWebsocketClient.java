package com.example.tradingapp.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.net.URI;

@Slf4j
@Component
@RequiredArgsConstructor
public class OkxWebsocketClient {

    private final OkxWebsocketHandler okxWebsocketHandler;
    @Value("${okx.websocket.url}")
    private String URL;

    public void connectOkx() {
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        headers.add("x-simulated-trading", "1");
        client.doHandshake(okxWebsocketHandler, headers, URI.create(URL));

        log.info("Connecting");
    }
}
