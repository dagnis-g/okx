package com.example.tradingapp.strategy;

import com.example.tradingapp.tracker.Order;
import com.example.tradingapp.tracker.OrderStatus;
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
            var order = Order.builder()
                    .status(OrderStatus.New)
                    .symbol(orderRequest.getSymbol())
                    .side(orderRequest.getSide())
                    .type(orderRequest.getType())
                    .price(orderRequest.getPrice())
                    .quantity(orderRequest.getQuantity())
                    .build();
            orderTracker.create(order);
        } else {
            log.error("Order not placed {}", orderResponseStatus.getErrorMessage());
        }
    }
}
