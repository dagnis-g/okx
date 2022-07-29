package com.example.tradingapp.deribit.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.util.concurrent.ExecutionException;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeribitWebsocketClient {
    private final DeribitWebsocketHandler websocketHandler;
    @Value("${deribit.websocket.url}")
    private String URL;

    public void connectDeribit() throws ExecutionException, InterruptedException {
        log.info("Connecting to Deribit {}", URL);
        WebSocketClient client = new StandardWebSocketClient();
        client.doHandshake(websocketHandler, URL);
    }

}
