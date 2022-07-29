package com.example.tradingapp.deribit.websocket.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeribitResponseResultsPositions {

    @JsonProperty("instrument_name")
    private String currencyName;
    @JsonProperty("kind")
    private String instrumentType;
    private String direction;

}
