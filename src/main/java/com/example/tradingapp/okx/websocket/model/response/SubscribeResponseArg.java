package com.example.tradingapp.okx.websocket.model.response;

import lombok.Data;

@Data
public class SubscribeResponseArg {
    private String channel;
    private String instId;
}
