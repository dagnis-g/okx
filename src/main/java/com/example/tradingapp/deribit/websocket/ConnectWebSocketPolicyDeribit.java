package com.example.tradingapp.deribit.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConnectWebSocketPolicyDeribit {

    private final DeribitWebsocketClient webSocketClient;
    
    @Scheduled(initialDelay = 1000, fixedDelay = Long.MAX_VALUE)
    public void connectToWebSocket() throws ExecutionException, InterruptedException {
        log.info("Starting websocket connection Deribit");
        webSocketClient.connectDeribit();
    }
}
