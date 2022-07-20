package com.example.tradingapp.websocket.model.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SubscribeArg {
    private String channel;
    private String instType;
    private String ccy;
}
