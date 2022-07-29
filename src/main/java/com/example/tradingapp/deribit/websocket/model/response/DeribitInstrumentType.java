package com.example.tradingapp.deribit.websocket.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DeribitInstrumentType {
    @JsonProperty("future")
    FUTURE,
    @JsonProperty("option")
    OPTION
}
