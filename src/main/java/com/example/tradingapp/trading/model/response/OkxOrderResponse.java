package com.example.tradingapp.trading.model.response;

import lombok.Data;

import java.util.List;

@Data
public class OkxOrderResponse {
    private Integer code;
    private List<OkxOrderResponseData> data;
    private String msg;
}
