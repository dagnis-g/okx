package com.example.tradingapp.okx.strategy;

import com.example.tradingapp.tracker.Order;
import com.example.tradingapp.tracker.OrderStatus;
import com.example.tradingapp.okx.trading.OkxOrderTracker;
import com.example.tradingapp.okx.trading.model.request.OrderRequest;
import com.example.tradingapp.okx.trading.sender.NewOrderSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class NewOrderPolicy {

    private final NewOrderSender newOrderSender;
    private final OkxOrderTracker orderTracker;

    public void sendNewOrder(OrderRequest orderRequest) throws IOException {
        var orderResponseStatus = newOrderSender.send(orderRequest);
        if (orderResponseStatus.isSuccess()) {
            var order = Order.builder()
                    .id(orderResponseStatus.getOrderId())
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
