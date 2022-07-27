package com.example.tradingapp.deribit.fix.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeribitOrderStatus {

    New('0'),
    PartiallyFilled('1'),
    Filled('2'),
    Canceled('4'),
    Rejected('8');

    private final char status;

    public static DeribitOrderStatus parse(char status) {
        for (DeribitOrderStatus deribitOrderStatus : DeribitOrderStatus.values()) {
            if (deribitOrderStatus.getStatus() == status) {
                return deribitOrderStatus;
            }
        }
        return null;
    }
}
