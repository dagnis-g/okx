package com.example.tradingapp.trading;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderRequest {
    @JsonProperty("instId")
    private String symbol;
    private Side side;
    @JsonProperty("ordType")
    private OrderType type;
    @JsonProperty("px")
    private double price;
    @JsonProperty("sz")
    private double quantity;
}
