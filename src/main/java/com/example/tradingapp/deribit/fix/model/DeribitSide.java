package com.example.tradingapp.deribit.fix.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeribitSide {
    
    BUY('1'),
    SELL('2');

    private final char side;

    public static DeribitSide parse(char side) {
        for (DeribitSide deribitSide : DeribitSide.values()) {
            if (deribitSide.getSide() == side) {
                return deribitSide;
            }
        }
        return null;
    }
}
