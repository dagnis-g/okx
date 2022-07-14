package com.example.tradingapp.strategy;

import com.example.tradingapp.trading.NewOrderSender;
import com.example.tradingapp.trading.OrderTracker;
import com.example.tradingapp.trading.model.OrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class NewOrderPolicy {

    private final NewOrderSender newOrderSender;
    private final OrderTracker orderTracker;

    public void sendNewOrder(OrderRequest orderRequest) throws IOException {
        var orderResponseStatus = newOrderSender.send(orderRequest);
        if (orderResponseStatus.isSuccess()) {
            // todo: Add to order tracker
            System.out.println("pievinot trackerim " + orderResponseStatus);
        } else {
            // todo: log.error
            log.error("Order not placed {}", orderResponseStatus.getErrorMessage());
        }
    }
}
