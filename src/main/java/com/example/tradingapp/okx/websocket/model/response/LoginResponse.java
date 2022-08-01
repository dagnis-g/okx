package com.example.tradingapp.okx.websocket.model.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String event;
    private String msg;
    private String code;
}
