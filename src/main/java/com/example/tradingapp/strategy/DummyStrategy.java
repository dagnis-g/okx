package com.example.tradingapp.strategy;

import com.example.tradingapp.tracker.Order;
import com.example.tradingapp.trading.OkxOrderTracker;
import com.example.tradingapp.trading.model.enums.OrderType;
import com.example.tradingapp.trading.model.enums.Side;
import com.example.tradingapp.trading.model.request.OkxCancelOrderRequest;
import com.example.tradingapp.trading.model.request.OrderRequest;
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
    private final OkxOrderTracker orderTracker;
    private final CancelOrderPolicy cancelOrderPolicy;
    private final Random random = new Random();

    public void execute() throws IOException {
        var orderRequest = OrderRequest.builder()
                .symbol("BTC-USDT")
                .side(random.nextBoolean() ? Side.BUY : Side.SELL)
                .type(OrderType.LIMIT)
                .price(1)
                .quantity(0.0001)
                .build();

        log.info("Sending new order: {}", orderRequest);
        newOrderPolicy.sendNewOrder(orderRequest);
    }

    public void cancelOrder() throws IOException {
        String orderId = orderTracker.getPlacedOrderIds().get(0);
        Order orderToCancel = orderTracker.getPlacedOrders().get(orderId);

        var request = new OkxCancelOrderRequest();
        request.setOrderId(orderId);
        request.setSymbol(orderToCancel.getSymbol());

        log.info("Canceling order: {}", request);
        cancelOrderPolicy.cancelOrder(request);
        
    }
}

