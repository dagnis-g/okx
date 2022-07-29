package com.example.tradingapp.deribit.websocket.model.response;

import com.example.tradingapp.okx.trading.model.enums.Side;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeribitResponseResultsPositions {

    @JsonProperty("instrument_name")
    private String currencyName;
    @JsonProperty("kind")
    private DeribitInstrumentType instrumentType;
    private Side direction;

}
