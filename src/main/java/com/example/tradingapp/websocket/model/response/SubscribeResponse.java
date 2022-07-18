package com.example.tradingapp.websocket.model.response;

import lombok.Data;

@Data
public class SubscribeResponse {
    private String event;
    private SubscribeResponseArg arg;
}
