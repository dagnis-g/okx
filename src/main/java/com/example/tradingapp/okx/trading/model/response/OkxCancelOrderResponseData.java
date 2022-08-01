package com.example.tradingapp.okx.trading.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OkxCancelOrderResponseData {

    @JsonProperty("clOrdId")
    private String clientSupliedId;
    @JsonProperty("ordId")
    private String orderId;
    @JsonProperty("sCode")
    private String successCode;
    @JsonProperty("sMsg")
    private String errorMessage;

}
