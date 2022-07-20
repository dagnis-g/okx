package com.example.tradingapp.positions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Position {
    private String positionId;
    private String currency;
    private String instrumentType;
    private String positionSide;
}
