package com.example.tradingapp.okx.trading.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OkxOrderResponseData {
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
