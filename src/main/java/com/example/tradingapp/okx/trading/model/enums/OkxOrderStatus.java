package com.example.tradingapp.okx.trading.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OkxOrderStatus {
    @JsonProperty("live")
    Live,
    @JsonProperty("canceled")
    Canceled,
    @JsonProperty("filled")
    FullyFilled,
    @JsonProperty("partially_filled")
    PartiallyFilled
}
