package com.example.tradingapp.trading.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OkxOrderRequest {

    @JsonProperty("instId")
    private String instrumentId;
    @JsonProperty("tdMode")
    private String tradeMode;
    private String side;
    @JsonProperty("ordType")
    private String orderType;
    @JsonProperty("px")
    private BigDecimal orderPrice;
    @JsonProperty("sz")
    private BigDecimal quantity;

}
