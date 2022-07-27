package com.example.tradingapp.deribit.fix;

import com.example.tradingapp.deribit.fix.model.DeribitOrderStatus;
import com.example.tradingapp.deribit.fix.model.DeribitOrderType;
import com.example.tradingapp.deribit.fix.model.DeribitSide;
import org.junit.jupiter.api.Test;
import quickfix.FieldNotFound;
import quickfix.fix44.ExecutionReport;

import static org.assertj.core.api.Assertions.assertThat;

class DeribitExecutionReportDecoderTest {
    
    DeribitExecutionReportDecoder decoder = new DeribitExecutionReportDecoder();

    @Test
    void shouldDecodeExecutionReportCorrectly() throws FieldNotFound {
        var executionReport = new ExecutionReport();
        executionReport.setString(8, "FIX.4.4");
        executionReport.setString(9, "338");
        executionReport.setString(35, "8");
        executionReport.setString(34, "1");
        executionReport.setString(11, "13247477536");
        executionReport.setString(37, "13247477536");
        executionReport.setString(39, "0");
        executionReport.setString(40, "2");
        executionReport.setString(41, "516dbf23-e56e-435b-90e5-1183a490fa78");
        executionReport.setString(44, "3000");
        executionReport.setString(54, "1");
        executionReport.setString(55, "BTC-PERPETUAL");
        executionReport.setString(103, "0");
        executionReport.setString(58, "success");
        executionReport.setString(38, "1");

        var order = decoder.decode(executionReport);

        assertThat(order.getSymbol()).isEqualTo("BTC-PERPETUAL");
        assertThat(order.getDeribitId()).isEqualTo("13247477536");
        assertThat(order.getClientId()).isEqualTo("516dbf23-e56e-435b-90e5-1183a490fa78");
        assertThat(order.getStatus()).isEqualTo(DeribitOrderStatus.New);
        assertThat(order.getSide()).isEqualTo(DeribitSide.BUY);
        assertThat(order.getType()).isEqualTo(DeribitOrderType.LIMIT);
        assertThat(order.getQuantity()).isEqualTo(1);
        assertThat(order.getPrice()).isEqualTo(3000);
        assertThat(order.getRejectedReason()).isEqualTo(0);
        assertThat(order.getText()).isEqualTo("success");
    }

}