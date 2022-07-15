package com.example.tradingapp.strategy;

import com.example.tradingapp.trading.OrderTracker;
import com.example.tradingapp.trading.model.request.OkxCancelOrderRequest;
import com.example.tradingapp.trading.sender.CancelOrderSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CancelOrderPolicy {

    private final CancelOrderSender cancelOrderSender;
    private final OrderTracker orderTracker;

    public void cancelOrder(OkxCancelOrderRequest orderRequest) throws IOException {
        var orderResponseStatus = cancelOrderSender.cancel(orderRequest);

        if (orderResponseStatus.isSuccess()) {
            orderTracker.canceled(orderRequest.getOrderId());
        } else {
            log.error("Order Not Canceled {}", orderResponseStatus);
        }

    }

}
