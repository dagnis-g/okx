package com.example.tradingapp.okx.trading.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OkxCancelOrderRequest {

    @JsonProperty("ordId")
    private String orderId;
    @JsonProperty("instId")
    private String symbol;

}
