package com.example.tradingapp.tracker;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatus {

    New(false),
    @JsonProperty("live")
    Live(false),
    Failed(true),
    Canceled(true),
    FullyFilled(true);

    @Getter
    private final boolean terminal;

}
