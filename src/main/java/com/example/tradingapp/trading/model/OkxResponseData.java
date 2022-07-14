package com.example.tradingapp.trading.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OkxResponseData {
    @JsonProperty("clOrdId")
    private String clientSuppliedOrderId;
    @JsonProperty("ordId")
    private String orderId;
    @JsonProperty("sCode")
    private String successCode;
    @JsonProperty("sMsg")
    private String message;
    @JsonProperty("tag")
    private String orderTag;
}
