package com.example.tradingapp.account.model;

import lombok.Data;

import java.util.List;

@Data
public class OkxBalanceResponse {
    private String code;
    private List<OkxBalanceResponseData> data;
    private String msg;
}
