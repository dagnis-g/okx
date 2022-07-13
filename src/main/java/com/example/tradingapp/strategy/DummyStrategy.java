package com.example.tradingapp.strategy;

import com.example.tradingapp.trading.model.OrderRequest;
import com.example.tradingapp.trading.model.OrderType;
import com.example.tradingapp.trading.model.Side;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class DummyStrategy {

    private final NewOrderPolicy newOrderPolicy;
    private final Random random = new Random();
    
    public void execute() throws IOException {
        var orderRequest = OrderRequest.builder()
                .symbol("BTC-USDT")
                .side(random.nextBoolean() ? Side.buy : Side.sell)
                .type(OrderType.limit)
                .price(20)
                .quantity(1)
                .build();

        log.info("Sending new order: {}", orderRequest);
        newOrderPolicy.sendNewOrder(orderRequest);
    }
}

