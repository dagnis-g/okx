package com.example.tradingapp.okx.strategy;

import com.example.tradingapp.okx.trading.model.request.OkxCancelOrderRequest;
import com.example.tradingapp.okx.trading.sender.CancelOrderSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CancelOrderPolicy {

    private final CancelOrderSender cancelOrderSender;

    public void cancelOrder(OkxCancelOrderRequest orderRequest) throws IOException {
        var orderResponseStatus = cancelOrderSender.cancel(orderRequest);

        if (orderResponseStatus.isSuccess()) {
            log.info("Accepted Cancel Request {}", orderRequest);
        } else {
            log.error("Order Not Canceled {}", orderResponseStatus);
        }

    }

}
