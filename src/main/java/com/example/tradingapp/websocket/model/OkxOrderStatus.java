package com.example.tradingapp.websocket.model;

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
