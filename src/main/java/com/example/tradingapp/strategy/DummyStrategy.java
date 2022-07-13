package com.example.tradingapp.strategy;

import com.example.tradingapp.trading.model.OkxOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class DummyStrategy {

    private final NewOrderPolicy newOrderPolicy;
    private final Random random = new Random();

    //    public void execute() {
//        var orderRequest = OrderRequest.builder()
//                .price(20000)
//                .quantity(1)
//                .side(random.nextBoolean() ? Buy : Sell)
//                .type(OrderType.LIMIT)
//                .build();
//
//        log.info("Sending new order: {}", orderRequest);
//        newOrderPolicy.sendNewOrder(orderRequest);
//    }
    public void execute() throws IOException {
        var orderRequest = OkxOrderRequest.builder()
                .instrumentId("BTC-USDT")
                .tradeMode("cash")
                .side(random.nextBoolean() ? "buy" : "sell")
                .orderType("limit")
                .orderPrice(BigDecimal.valueOf(20))
                .quantity(BigDecimal.valueOf(1))
                .build();

        log.info("Sending new order: {}", orderRequest);
        newOrderPolicy.sendNewOrder(orderRequest);
    }
}

