package com.example.tradingapp.trading.model.response;

import com.example.tradingapp.tracker.OrderStatus;
import com.example.tradingapp.trading.model.enums.OrderType;
import com.example.tradingapp.trading.model.enums.Side;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OkxOrderListResponseData {
    @JsonProperty("ordId")
    private String id;
    @JsonProperty("state")
    private OrderStatus status;
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
