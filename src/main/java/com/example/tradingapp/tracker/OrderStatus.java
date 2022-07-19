package com.example.tradingapp.tracker;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatus {

    New(false),
    @JsonProperty("live")
    Live(false),
    @JsonProperty("failed")
    Failed(true),
    @JsonProperty("canceled")
    Canceled(true),
    @JsonProperty("filled")
    FullyFilled(true),
    @JsonProperty("partially_filled")
    PartiallyFilled(false);

    @Getter
    private final boolean terminal;

}
