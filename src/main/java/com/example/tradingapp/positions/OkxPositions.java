package com.example.tradingapp.positions;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
public class OkxPositions {
    private final Map<String, OkxPositionsResponseData> positions;
}
