package com.example.tradingapp.positions;

import com.example.tradingapp.deribit.fix.messaging.DeribitPositionsPublisher;
import com.example.tradingapp.deribit.websocket.model.response.DeribitResponse;
import com.example.tradingapp.okx.messaging.OkxPositionsPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Data
@Component
public class Positions {

    private final Map<String, Position> positionsOkx;
    private final Map<String, Position> positionsDeribit;

    private final OkxPositionsPublisher okxPublisher;
    private final DeribitPositionsPublisher deribitPublisher;

    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public void updatePositionsOkx(OkxPositionsResponseData data) {
        var position = Position.builder()
                .positionId(data.getPositionId())
                .currency(data.getCurrency())
                .instrumentType(data.getInstrumentType())
                .positionSide(data.getPositionSide())
                .build();
        var pos = data.getPositionSide();
        positionsOkx.put(data.getPositionId(), position);

        try {
            String jsonBalance = mapper.writeValueAsString(getPositionsOkx());
            okxPublisher.sendEvent(jsonBalance);
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
    }

    public void updatePositionsDeribit(JsonNode jsonNode) throws JsonProcessingException {
        var deribitResponse = mapper.treeToValue(jsonNode, DeribitResponse.class);

        for (var result : deribitResponse.getResult()) {
            String currency = result.getCurrencyName().split("-")[0];
            var position = Position.builder()
                    .positionId(result.getCurrencyName())
                    .currency(currency)
                    .instrumentType(result.getInstrumentType().toString())
                    .positionSide(result.getDirection().toString())
                    .build();

            getPositionsDeribit().put(position.getPositionId(), position);
            log.info("Added to deribit positions: {}", position);
        }

        log.info("Deribit positions: {}", getPositionsDeribit());

        try {
            String jsonBalance = mapper.writeValueAsString(getPositionsDeribit());
            deribitPublisher.sendEvent(jsonBalance);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
