package com.example.tradingapp.positions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OkxPositionsResponseData {

    @JsonProperty("posId")
    private String positionId;
    @JsonProperty("ccy")
    private String currency;
    @JsonProperty("instType")
    private String instrumentType;
    @JsonProperty("posSide")
    private String positionSide;


}
