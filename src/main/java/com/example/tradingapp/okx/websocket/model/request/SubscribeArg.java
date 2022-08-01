package com.example.tradingapp.okx.websocket.model.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SubscribeArg {
    private String channel;
    private String instType;
    private String ccy;
}
