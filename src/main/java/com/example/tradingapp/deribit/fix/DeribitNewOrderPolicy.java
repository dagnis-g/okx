package com.example.tradingapp.deribit.fix;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import quickfix.Message;
import quickfix.SessionNotFound;
import quickfix.fix44.NewOrderSingle;

import static quickfix.Session.sendToTarget;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeribitNewOrderPolicy {

    @Scheduled(initialDelay = 6000, fixedDelay = Long.MAX_VALUE)
    public void placeOrder() throws SessionNotFound {

        Message message = new NewOrderSingle();
        message.setString(11, "123456789");
        message.setString(54, "1");
        message.setString(38, "1");
        message.setString(44, "3");
        message.setString(55, "BTC-29JUL22");

        sendToTarget(message, "28work", "DERIBITSERVER");

        log.info("Sending order message {}", message);
    }

}
