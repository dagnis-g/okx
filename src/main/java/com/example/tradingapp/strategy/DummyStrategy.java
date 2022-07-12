package com.example.tradingapp.strategy;

import com.example.tradingapp.trading.OrderRequest;
import com.example.tradingapp.trading.OrderType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;

import static com.example.tradingapp.trading.Side.Buy;
import static com.example.tradingapp.trading.Side.Sell;

@Component
@RequiredArgsConstructor
@Slf4j
public class DummyStrategy {

    private final NewOrderPolicy newOrderPolicy;
    private final Random random = new Random();

    public void execute() {
        var orderRequest = OrderRequest.builder()
                .price(20000)
                .quantity(1)
                .side(random.nextBoolean() ? Buy : Sell)
                .type(OrderType.LIMIT)
                .build();

        log.info("Sending new order: {}", orderRequest);
        newOrderPolicy.sendNewOrder(orderRequest);
    }
}

