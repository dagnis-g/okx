package com.example.tradingapp.trading.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OrderType {
    @JsonProperty("market")
    MARKET,
    @JsonProperty("limit")
    LIMIT
}
