package com.example.tradingapp.trading.model;

import lombok.Data;

import java.util.List;

@Data
public class OkxOrderListResponse {

    private String code;
    private List<OrderListResponseData> data;
    
}
