package com.example.tradingapp.trading.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OkxCancelOrderRequest {

    @JsonProperty("ordId")
    private String orderId;
    @JsonProperty("instId")
    private String symbol;

}
