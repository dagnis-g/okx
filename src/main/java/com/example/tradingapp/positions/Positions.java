package com.example.tradingapp.positions;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
public class Positions {
    private final Map<String, Position> positions;

    public void updatePositionsFromOkx(OkxPositionsResponseData data) {
        var position = Position.builder()
                .positionId(data.getPositionId())
                .currency(data.getCurrency())
                .instrumentType(data.getInstrumentType())
                .positionSide(data.getPositionSide())
                .build();
        positions.put(data.getPositionId(), position);
    }
}
