package com.example.tradingapp.trading.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OkxOrderRequest {
    
    @JsonProperty("instId")
    private String symbol;
    @JsonProperty("tdMode")
    private String tradeMode;
    private Side side;
    @JsonProperty("ordType")
    private OrderType type;
    @JsonProperty("px")
    private BigDecimal price;
    @JsonProperty("sz")
    private BigDecimal quantity;

}
