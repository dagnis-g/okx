package com.example.tradingapp.deribit.fix.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeribitSide {
    //    @JsonProperty("1")
    BUY('1'),
    //    @JsonProperty("2")
    SELL('2');

    private final char side;

//    DeribitSide(String side) {
//        this.side = side;
//    }
//
//    String getSide() {
//        return side;
//    }

    public static DeribitSide parse(char side) {
        for (DeribitSide deribitSide : DeribitSide.values()) {
            if (deribitSide.getSide() == side) {
                return deribitSide;
            }
        }
        return null;
    }
}
