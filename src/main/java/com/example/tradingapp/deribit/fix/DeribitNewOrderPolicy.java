package com.example.tradingapp.deribit.fix;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import quickfix.FieldNotFound;
import quickfix.Message;
import quickfix.SessionNotFound;

import java.util.UUID;

import static quickfix.Session.sendToTarget;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeribitNewOrderPolicy {

    //    @Scheduled(initialDelay = 3000, fixedDelay = Long.MAX_VALUE)
    @Scheduled(fixedRate = 3000, initialDelay = 3000)
    public void placeOrder() throws SessionNotFound, FieldNotFound {
        UUID uuid = UUID.randomUUID();
        Message message = new quickfix.fix44.NewOrderSingle();
        message.setString(11, String.valueOf(uuid));
        message.setString(54, "2");
        message.setString(38, "1");
        message.setString(44, "3");
        message.setString(55, "BTC-PERPETUAL");

        sendToTarget(message, "28work", "DERIBITSERVER");

        log.info("Sending order message {}", message);
    }

}
