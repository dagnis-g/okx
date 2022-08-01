package com.example.tradingapp.deribit.websocket;

import com.example.tradingapp.deribit.websocket.model.request.DeribitRequest;
import com.example.tradingapp.deribit.websocket.model.request.DeribitRequestParamsPosition;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeribitPositionPolicy {

    private final DeribitLoginStateWS loginStateWS;
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${deribit.symbols.enabled}")
    private String symbols;
    
    public void getPositions() throws IOException {
        if (loginStateWS.isLoggedIn()) {
            var symbolsArray = symbols.split(",");
            for (String symbol : symbolsArray) {
                var deribitRequest = buildPositionsRequest(symbol);
                String requestJson = mapper.writeValueAsString(deribitRequest);
                log.info("Sending get Positions request: {}", requestJson);
                loginStateWS.getSession().sendMessage(new TextMessage(requestJson));
            }
        }
    }

    private DeribitRequest buildPositionsRequest(String symbol) {
        var positionParams = DeribitRequestParamsPosition.builder()
                .currency(symbol)
                .kind("any")
                .build();
        return DeribitRequest.builder()
                .id("positions")
                .method("private/get_positions")
                .params(positionParams)
                .build();
    }
}
