package com.example.tradingapp.deribit.fix;

import com.example.tradingapp.NoOrderFoundException;
import com.example.tradingapp.trading.model.request.OrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import quickfix.SessionNotFound;
import quickfix.fix44.OrderCancelRequest;

import static quickfix.Session.sendToTarget;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeribitCancelOrderSender {

    private final DeribitOrderTracker orderTracker;

    public void cancel(OrderRequest orderRequest) throws SessionNotFound, NoOrderFoundException {
        var order = orderTracker.findOrderByOrderRequest(orderRequest);
        var message = new OrderCancelRequest();
        message.setString(41, order.getId());

        sendToTarget(message, "28work", "DERIBITSERVER");
    }

}
