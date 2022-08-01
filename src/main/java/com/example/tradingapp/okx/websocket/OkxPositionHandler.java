package com.example.tradingapp.okx.websocket;

import com.example.tradingapp.positions.OkxPositionsResponse;
import com.example.tradingapp.positions.Positions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OkxPositionHandler {
    private final Positions positions;
    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public void handlePositions(JsonNode jsonNode) throws JsonProcessingException {
        var positionResponse = mapper.treeToValue(jsonNode, OkxPositionsResponse.class);

        if (positionResponse.getData().size() > 0) {
            var positionData = positionResponse.getData().get(0);
            positions.updatePositionsOkx(positionData);

            log.info("Current Future positions from Websocket {}", positions.getPositionsOkx());
        } else {
            log.info("No active Future positions");
        }

    }
}