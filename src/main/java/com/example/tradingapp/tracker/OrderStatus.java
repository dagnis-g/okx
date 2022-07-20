package com.example.tradingapp.tracker;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatus {

    New(false),
    Live(false),
    Failed(true),
    Canceled(true),
    FullyFilled(true);

    @Getter
    private final boolean terminal;

}
