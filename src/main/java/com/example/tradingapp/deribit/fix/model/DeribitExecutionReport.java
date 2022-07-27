package com.example.tradingapp.deribit.fix.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeribitExecutionReport {
    //    "Symbol"
    private String symbol;
    //    "ClOrdID"
    private String deribitId;
    //    "OrigClOrdId"
    private String clientId;
    //    "OrdStatus"
    private DeribitOrderStatus status;
    //    "Side"
    private DeribitSide side;
    //    "OrdType"
    private DeribitOrderType type;
    //    "OrderQty"
    private double quantity;
    //    "Price"
    private double price;
    //    "OrdRejReason"
    private Integer rejectedReason;
    //    "Text"
    private String text;

}
