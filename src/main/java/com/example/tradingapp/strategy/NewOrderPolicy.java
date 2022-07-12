package com.example.tradingapp.strategy;

import com.example.tradingapp.trading.NewOrderSender;
import com.example.tradingapp.trading.OrderRequest;
import com.example.tradingapp.trading.OrderTracker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NewOrderPolicy {

    private final NewOrderSender newOrderSender;
    private final OrderTracker orderTracker;

    public void sendNewOrder(OrderRequest orderRequest) {
        var orderResponseStatus = newOrderSender.send(orderRequest);
        if (orderResponseStatus.isSuccess()) {
            // todo: Add to order tracker
        } else {
            // todo: log.error
        }
    }
}
