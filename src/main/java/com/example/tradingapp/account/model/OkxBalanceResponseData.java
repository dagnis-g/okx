package com.example.tradingapp.account.model;

import lombok.Data;

import java.util.List;

@Data
public class OkxBalanceResponseData {
    List<OkxBalanceResponseDataDetails> details;
}
