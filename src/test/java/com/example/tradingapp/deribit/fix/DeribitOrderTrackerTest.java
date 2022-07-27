package com.example.tradingapp.deribit.fix;

import com.example.tradingapp.tracker.OrderStatus;
import com.example.tradingapp.trading.OkxOrderTracker;
import com.example.tradingapp.trading.model.enums.OrderType;
import com.example.tradingapp.trading.model.enums.Side;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import quickfix.FieldNotFound;
import quickfix.IncorrectTagValue;
import quickfix.SessionID;
import quickfix.UnsupportedMessageType;
import quickfix.fix44.ExecutionReport;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DeribitOrderTrackerTest {

    @Autowired
    DeribitOrderTracker deribitOrderTracker;

    @Autowired
    OkxOrderTracker okxOrderTracker;

    @Autowired
    DeribitFixMessageCracker messageCracker;

    @Test
    void shouldAddCorrectOrderFromExecutionReportToDeribitTrackerAndNotOkx() throws UnsupportedMessageType, IncorrectTagValue, FieldNotFound {
        var executionReport = new ExecutionReport();
        executionReport.setString(8, "FIX.4.4");
        executionReport.setString(9, "338");
        executionReport.setString(35, "8");
        executionReport.setString(34, "1");
        executionReport.setString(11, "132");
        executionReport.setString(37, "132");
        executionReport.setString(39, "0");
        executionReport.setString(40, "2");
        executionReport.setString(41, "516dbf23-e56e-435b-90e5-1183a490fa78");
        executionReport.setString(44, "3000");
        executionReport.setString(54, "1");
        executionReport.setString(55, "BTC-PERPETUAL");
        executionReport.setString(103, "0");
        executionReport.setString(58, "success");
        executionReport.setString(38, "1");

        messageCracker.crack(executionReport, new SessionID("FIX.4.4:28work->DERIBITSERVER"));

        var deribitOrders = deribitOrderTracker.getPlacedOrders();
        var okxOrders = okxOrderTracker.getPlacedOrders();

        var deribitOrder = deribitOrderTracker.getPlacedOrders().get("132");

        var okxOrder = okxOrderTracker.getPlacedOrders().get("132");
        
        assertThat(deribitOrders).isNotEqualTo(okxOrders);

        assertThat(deribitOrder.getId()).isEqualTo("132");
        assertThat(deribitOrder.getStatus()).isEqualTo(OrderStatus.Live);
        assertThat(deribitOrder.getSymbol()).isEqualTo("BTC-PERPETUAL");
        assertThat(deribitOrder.getSide()).isEqualTo(Side.BUY);
        assertThat(deribitOrder.getType()).isEqualTo(OrderType.LIMIT);
        assertThat(deribitOrder.getPrice()).isEqualTo(3000);
        assertThat(deribitOrder.getQuantity()).isEqualTo(1);

        assertThat(okxOrder).isEqualTo(null);
    }

}