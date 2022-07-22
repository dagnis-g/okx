package com.example.tradingapp.strategy;

import com.example.tradingapp.trading.OkxOrderTracker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class DummyStrategy {

    private final NewOrderPolicy newOrderPolicy;
    private final OkxOrderTracker orderTracker;
    private final CancelOrderPolicy cancelOrderPolicy;
    private final Random random = new Random();

//    public void cancelOrder() throws IOException {
//        if (orderTracker.getPlacedOrderIds().size() > 0) {
//            String orderId = orderTracker.getPlacedOrderIds().get(0);
//            Order orderToCancel = orderTracker.getPlacedOrders().get(orderId);
//
//            var request = new OkxCancelOrderRequest();
//            request.setOrderId(orderId);
//            request.setSymbol(orderToCancel.getSymbol());
//
//            log.info("Canceling order: {}", request);
//            cancelOrderPolicy.cancelOrder(request);
//        }
//
//    }
}

