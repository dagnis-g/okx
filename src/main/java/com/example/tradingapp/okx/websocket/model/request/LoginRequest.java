package com.example.tradingapp.okx.websocket.model.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class LoginRequest {
    private String op;
    private List<LoginArg> args;
}
