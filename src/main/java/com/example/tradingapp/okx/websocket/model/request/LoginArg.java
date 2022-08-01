package com.example.tradingapp.okx.websocket.model.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginArg {
    private String apiKey;
    private String passphrase;
    private String timestamp;
    private String sign;
}
