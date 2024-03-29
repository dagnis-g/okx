package com.example.tradingapp.trading.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponseStatus {

    private boolean success;
    private String orderId;

    private String errorCode;
    private String errorMessage;
}
