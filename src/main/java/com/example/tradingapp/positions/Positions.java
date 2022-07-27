package com.example.tradingapp.positions;

import com.example.tradingapp.messaging.OkxPublishPositions;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Data
@Component
public class Positions {

    private final Map<String, Position> positions;

    private final OkxPublishPositions publishPositions;
    private final ObjectMapper mapper = new ObjectMapper();
    
    public void updatePositionsFromOkx(OkxPositionsResponseData data) {
        var position = Position.builder()
                .positionId(data.getPositionId())
                .currency(data.getCurrency())
                .instrumentType(data.getInstrumentType())
                .positionSide(data.getPositionSide())
                .build();
        positions.put(data.getPositionId(), position);

        try {
            String jsonBalance = mapper.writeValueAsString(getPositions());
            publishPositions.sendEvent(jsonBalance);
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
    }
}
