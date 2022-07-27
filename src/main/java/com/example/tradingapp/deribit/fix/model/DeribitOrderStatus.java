package com.example.tradingapp.deribit.fix.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeribitOrderStatus {
    //    @JsonProperty("0")
    New('0'),
    //    @JsonProperty("1")
    PartiallyFilled('1'),
    //    @JsonProperty("2")
    Filled('2'),
    //    @JsonProperty("4")
    Canceled('4'),
    //    @JsonProperty("8")
    Rejected('8');

    private final char status;

//    DeribitOrderStatus(String status) {
//        this.status = status;
//    }
//
//    String getStatus() {
//        return status;
//    }

    public static DeribitOrderStatus parse(char status) {
        for (DeribitOrderStatus deribitOrderStatus : DeribitOrderStatus.values()) {
            if (deribitOrderStatus.getStatus() == status) {
                return deribitOrderStatus;
            }
        }
        return null;
    }
}
