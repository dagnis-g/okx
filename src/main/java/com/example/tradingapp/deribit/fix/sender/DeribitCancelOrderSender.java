package com.example.tradingapp.deribit.fix.sender;

import com.example.tradingapp.deribit.fix.DeribitOrderTracker;
import com.example.tradingapp.exceptions.NoOrderFoundException;
import com.example.tradingapp.okx.trading.model.request.OrderRequest;
import com.example.tradingapp.secrets.DeribitSecrets;
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

        sendToTarget(message, DeribitSecrets.SENDER_COMP_ID, DeribitSecrets.TARGET_COMP_ID);
    }

}
