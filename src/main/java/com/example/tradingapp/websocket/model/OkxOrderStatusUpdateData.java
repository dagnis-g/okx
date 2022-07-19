package com.example.tradingapp.websocket.model;

import com.example.tradingapp.tracker.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OkxOrderStatusUpdateData {
    @JsonProperty("ordId")
    private String orderId;
    private OrderStatus state;
}
