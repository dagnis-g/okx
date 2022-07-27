package com.example.tradingapp.deribit.fix.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeribitOrderType {
    //    @JsonProperty("1")
    MARKET('1'),
    //    @JsonProperty("2")
    LIMIT('2');

    private final char type;

    public static DeribitOrderType parse(char type) {
        for (DeribitOrderType deribitOrderType : DeribitOrderType.values()) {
            if (deribitOrderType.getType() == type) {
                return deribitOrderType;
            }
        }
        return null;
    }
}
