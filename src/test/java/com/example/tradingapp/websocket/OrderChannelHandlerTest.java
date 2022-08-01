package com.example.tradingapp.websocket;

import com.example.tradingapp.okx.trading.OkxOrderTracker;
import com.example.tradingapp.okx.trading.model.enums.OrderType;
import com.example.tradingapp.okx.trading.model.enums.Side;
import com.example.tradingapp.okx.websocket.OrderChannelHandler;
import com.example.tradingapp.scheduled.ScheduledStrategyExecutor;
import com.example.tradingapp.tracker.Order;
import com.example.tradingapp.tracker.OrderStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
class OrderChannelHandlerTest {

    @Autowired
    OrderChannelHandler channelHandler;
    @Autowired
    OkxOrderTracker orderTracker;
    ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    @Autowired
    ScheduledStrategyExecutor strategyExecutor;

    @Test
    void shouldChangeStatusNewToLiveAndLogTheChange(CapturedOutput output) throws JsonProcessingException {
        var orderToInsert = buildOrder(OrderStatus.New);

        orderTracker.getPlacedOrders().put(orderToInsert.getId(), orderToInsert);
        JsonNode jsonNode = mapper.readTree(jsonString);
        channelHandler.handleOrderStatus(jsonNode);

        var orderFromTracker = orderTracker.getPlacedOrders().get(orderToInsert.getId());

        assertThat(orderFromTracker.getStatus()).isEqualTo(OrderStatus.Live);
        assertThat(output.getOut()).contains("Order (1) status change: New -> Live");
    }

    @Test
    void shouldChangeStatusNewToFullyFilled() throws JsonProcessingException {
        var orderToInsert = buildOrder(OrderStatus.New);

        orderTracker.getPlacedOrders().put(orderToInsert.getId(), orderToInsert);
        String modifiedJsonString = jsonString.replace("live", "filled");
        JsonNode jsonNode = mapper.readTree(modifiedJsonString);
        channelHandler.handleOrderStatus(jsonNode);

        var orderFromTracker = orderTracker.getPlacedOrders().get(orderToInsert.getId());

        assertThat(orderFromTracker.getStatus()).isEqualTo(OrderStatus.FullyFilled);
    }

    @Test
    void shouldChangeStatusNewToCancelled() throws JsonProcessingException {
        var orderToInsert = buildOrder(OrderStatus.New);

        orderTracker.getPlacedOrders().put(orderToInsert.getId(), orderToInsert);
        String modifiedJsonString = jsonString.replace("live", "canceled");
        JsonNode jsonNode = mapper.readTree(modifiedJsonString);
        channelHandler.handleOrderStatus(jsonNode);

        var orderFromTracker = orderTracker.getPlacedOrders().get(orderToInsert.getId());

        assertThat(orderFromTracker.getStatus()).isEqualTo(OrderStatus.Canceled);
    }

    @Test
    void shouldChangeStatusNewToLiveToFilled() throws JsonProcessingException {
        var orderToInsert = buildOrder(OrderStatus.New);

        orderTracker.getPlacedOrders().put(orderToInsert.getId(), orderToInsert);

        JsonNode jsonNode = mapper.readTree(jsonString);
        channelHandler.handleOrderStatus(jsonNode);
        var orderFromTrackerLive = orderTracker.getPlacedOrders().get(orderToInsert.getId());

        assertThat(orderFromTrackerLive.getStatus()).isEqualTo(OrderStatus.Live);

        String modifiedJsonStringFilled = jsonString.replace("live", "filled");
        JsonNode jsonNodeFilled = mapper.readTree(modifiedJsonStringFilled);
        channelHandler.handleOrderStatus(jsonNodeFilled);
        var orderFromTrackerFilled = orderTracker.getPlacedOrders().get(orderToInsert.getId());

        assertThat(orderFromTrackerFilled.getStatus()).isEqualTo(OrderStatus.FullyFilled);
    }

    @Test
    void shouldChangeStatusNewToLiveToCanceled() throws JsonProcessingException {
        var orderToInsert = buildOrder(OrderStatus.New);

        orderTracker.getPlacedOrders().put(orderToInsert.getId(), orderToInsert);

        JsonNode jsonNode = mapper.readTree(jsonString);
        channelHandler.handleOrderStatus(jsonNode);
        var orderFromTrackerLive = orderTracker.getPlacedOrders().get(orderToInsert.getId());

        assertThat(orderFromTrackerLive.getStatus()).isEqualTo(OrderStatus.Live);

        String modifiedJsonStringCanceled = jsonString.replace("live", "canceled");
        JsonNode jsonNodeCanceled = mapper.readTree(modifiedJsonStringCanceled);
        channelHandler.handleOrderStatus(jsonNodeCanceled);
        var orderFromTrackerCanceled = orderTracker.getPlacedOrders().get(orderToInsert.getId());

        assertThat(orderFromTrackerCanceled.getStatus()).isEqualTo(OrderStatus.Canceled);
    }

    @Test
    void shouldChangeStatusNewToLiveToStillLiveToFilled() throws JsonProcessingException {
        var orderToInsert = buildOrder(OrderStatus.New);

        orderTracker.getPlacedOrders().put(orderToInsert.getId(), orderToInsert);

        JsonNode jsonNode = mapper.readTree(jsonString);
        channelHandler.handleOrderStatus(jsonNode);
        var orderFromTrackerLive = orderTracker.getPlacedOrders().get(orderToInsert.getId());

        assertThat(orderFromTrackerLive.getStatus()).isEqualTo(OrderStatus.Live);

        String modifiedJsonStringPartiallyFilled = jsonString.replace("live", "partially_filled");
        JsonNode jsonNodePartiallyFilled = mapper.readTree(modifiedJsonStringPartiallyFilled);
        channelHandler.handleOrderStatus(jsonNodePartiallyFilled);
        var orderFromTrackerPartiallyFilled = orderTracker.getPlacedOrders().get(orderToInsert.getId());

        assertThat(orderFromTrackerPartiallyFilled.getStatus()).isEqualTo(OrderStatus.Live);

        String modifiedJsonStringFilled = jsonString.replace("live", "filled");
        JsonNode jsonNodeFilled = mapper.readTree(modifiedJsonStringFilled);
        channelHandler.handleOrderStatus(jsonNodeFilled);
        var orderFromTrackerFilled = orderTracker.getPlacedOrders().get(orderToInsert.getId());

        assertThat(orderFromTrackerFilled.getStatus()).isEqualTo(OrderStatus.FullyFilled);
    }

    @Test
    void shouldRemoveOrdersWithTerminalStatus() {
        var orderToInsert1 = buildOrder(OrderStatus.New);
        var orderToInsert2 = buildOrder(OrderStatus.Canceled);
        var orderToInsert3 = buildOrder(OrderStatus.FullyFilled);
        orderTracker.getPlacedOrders().put(orderToInsert1.getId(), orderToInsert1);
        orderTracker.getPlacedOrders().put(orderToInsert2.getId(), orderToInsert2);
        orderTracker.getPlacedOrders().put(orderToInsert3.getId(), orderToInsert3);

        strategyExecutor.removeTerminalOrdersOkx();

        orderTracker.getPlacedOrders()
                .forEach((key, value) -> assertThat(value.getStatus().isTerminal()).isEqualTo(false));
    }

    private Order buildOrder(OrderStatus status) {
        return Order.builder()
                .id("1")
                .status(status)
                .symbol("BTC")
                .side(Side.BUY)
                .type(OrderType.LIMIT)
                .price(1234)
                .quantity(1)
                .build();
    }

    String jsonString = """
            {
                "arg": {
                    "channel": "orders",
                    "instType": "SPOT",
                    "instId": "BTC",
                    "uid": "614488474791936"
                },
                "data": [
                    {
                        "accFillSz": "0.001",
                        "amendResult": "",
                        "avgPx": "31527.1",
                        "cTime": "1654084334977",
                        "category": "normal",
                        "ccy": "",
                        "clOrdId": "",
                        "code": "0",
                        "execType": "M",
                        "fee": "-0.02522168",
                        "feeCcy": "USDT",
                        "fillFee": "-0.02522168",
                        "fillFeeCcy": "USDT",
                        "fillNotionalUsd": "31.50818374",
                        "fillPx": "31527.1",
                        "fillSz": "0.001",
                        "fillTime": "1654084353263",
                        "instId": "BTC-USDT",
                        "instType": "SPOT",
                        "lever": "0",
                        "msg": "",
                        "notionalUsd": "31.50818374",
                        "ordId": "1",
                        "ordType": "limit",
                        "pnl": "0",
                        "posSide": "",
                        "px": "31527.1",
                        "rebate": "0",
                        "rebateCcy": "BTC",
                        "reduceOnly": "false",
                        "reqId": "",
                        "side": "sell",
                        "slOrdPx": "",
                        "slTriggerPx": "",
                        "slTriggerPxType": "last",
                        "source": "",
                        "state": "live",
                        "sz": "0.001",
                        "tag": "",
                        "tdMode": "cash",
                        "tgtCcy": "",
                        "tpOrdPx": "",
                        "tpTriggerPx": "",
                        "tpTriggerPxType": "last",
                        "tradeId": "242589207",
                        "uTime": "1654084353264"
                    }
                ]
            }""";
}