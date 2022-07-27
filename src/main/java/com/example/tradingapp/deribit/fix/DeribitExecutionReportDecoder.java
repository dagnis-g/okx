package com.example.tradingapp.deribit.fix;

import com.example.tradingapp.deribit.fix.model.DeribitExecutionReport;
import com.example.tradingapp.deribit.fix.model.DeribitOrderStatus;
import com.example.tradingapp.deribit.fix.model.DeribitOrderType;
import com.example.tradingapp.deribit.fix.model.DeribitSide;
import org.springframework.stereotype.Component;
import quickfix.FieldNotFound;
import quickfix.fix44.ExecutionReport;

@Component
public class DeribitExecutionReportDecoder {

    public DeribitExecutionReport decode(ExecutionReport message) throws FieldNotFound {
        return DeribitExecutionReport.builder()
                .symbol(message.getSymbol().getValue())
                .deribitId(message.getClOrdID().getValue())
                .clientId(message.getOrigClOrdID().getValue())
                .status(DeribitOrderStatus.parse(message.getOrdStatus().getValue()))
                .side(DeribitSide.parse(message.getSide().getValue()))
                .type(DeribitOrderType.parse(message.getOrdType().getValue()))
                .quantity(message.getOrderQty().getValue())
                .price(message.getPrice().getValue())
                .rejectedReason(message.getOrdRejReason().getValue())
                .text(message.getText().getValue())
                .build();
    }

}
