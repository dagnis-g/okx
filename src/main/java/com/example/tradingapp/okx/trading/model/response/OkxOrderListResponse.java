package com.example.tradingapp.okx.trading.model.response;

import lombok.Data;

import java.util.List;

@Data
public class OkxOrderListResponse {

    private String code;
    private List<OkxOrderListResponseData> data;

}
