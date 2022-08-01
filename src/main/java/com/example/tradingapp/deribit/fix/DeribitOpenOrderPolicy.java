package com.example.tradingapp.deribit.fix;

import com.example.tradingapp.secrets.DeribitSecrets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import quickfix.SessionNotFound;
import quickfix.field.MassStatusReqID;
import quickfix.field.MassStatusReqType;
import quickfix.fix44.OrderMassStatusRequest;

import java.util.UUID;

import static quickfix.Session.sendToTarget;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeribitOpenOrderPolicy {

    @Scheduled(initialDelay = 200, fixedDelay = Long.MAX_VALUE)
    public void getOpenOrders() throws SessionNotFound {
        UUID uuid = UUID.randomUUID();
        var message = new OrderMassStatusRequest(new MassStatusReqID(uuid.toString()), new MassStatusReqType(7));
        log.info("Order Mass Status Request(AF)");
        sendToTarget(message, DeribitSecrets.SENDER_COMP_ID, DeribitSecrets.TARGET_COMP_ID);
        log.info("Sending OrderMassStatusRequest {}", message);
    }

}
