package com.example.tradingapp.websocket.strategy;

import com.example.tradingapp.websocket.MyWebsocketClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConnectWebsocketPolicy {

    private final MyWebsocketClient webSocketClient;

    @Scheduled(initialDelay = 2000, fixedDelay = Long.MAX_VALUE)
    public void connectToWebSocket() {
        log.info("Starting websocket connection");
        webSocketClient.startWebSocketConnection();
    }
}
