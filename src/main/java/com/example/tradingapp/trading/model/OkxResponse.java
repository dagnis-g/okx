package com.example.tradingapp.trading.model;

import lombok.Data;

@Data
public class OkxResponse {
    private String code;
    private OkxResponseData data;
    private String msg;
}
