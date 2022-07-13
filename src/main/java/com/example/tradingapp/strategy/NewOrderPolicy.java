package com.example.tradingapp.strategy;

import com.example.tradingapp.trading.NewOrderSender;
import com.example.tradingapp.trading.model.OkxOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class NewOrderPolicy {

    private final NewOrderSender newOrderSender;
//    private final OrderTracker orderTracker;

    //    public void sendNewOrder(OrderRequest orderRequest) throws IOException {
//        var orderResponseStatus = newOrderSender.send(orderRequest);
//        if (orderResponseStatus.isSuccess()) {
//            // todo: Add to order tracker
//        } else {
//            // todo: log.error
//        }
//    }
    public void sendNewOrder(OkxOrderRequest orderRequest) throws IOException {
        var orderResponseStatus = newOrderSender.send(orderRequest);
        if (orderResponseStatus.isSuccess()) {
            // todo: Add to order tracker

        } else {
            // todo: log.error
        }
    }
}
