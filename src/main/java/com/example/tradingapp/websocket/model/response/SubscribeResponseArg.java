package com.example.tradingapp.websocket.model.response;

import lombok.Data;

@Data
public class SubscribeResponseArg {
    private String channel;
    private String instId;
}
