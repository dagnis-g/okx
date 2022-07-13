package com.example.tradingapp.trading.model;

import lombok.Data;

import java.util.List;

@Data
public class OkxResponse {
    private Integer code;
    private List<OkxResponseData> data;
    private String msg;
}
