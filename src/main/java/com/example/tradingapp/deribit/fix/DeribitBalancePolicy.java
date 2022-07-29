package com.example.tradingapp.deribit.fix;

import com.example.tradingapp.secrets.DeribitSecrets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import quickfix.SessionNotFound;
import quickfix.field.UserRequestID;
import quickfix.field.UserRequestType;
import quickfix.field.Username;
import quickfix.fix44.UserRequest;

import java.util.UUID;

import static quickfix.Session.sendToTarget;

@Slf4j
@Component
public class DeribitBalancePolicy {
    @Value("${deribit.symbols.enabled}")
    private String symbols;

    @Scheduled(initialDelay = 1000, fixedDelay = Long.MAX_VALUE)
    public void getBalance() throws SessionNotFound {
        String[] symbolsArray = symbols.split(",");

        for (String symbol : symbolsArray) {
            String requestId = UUID.randomUUID().toString();
            var message = new UserRequest(
                    new UserRequestID(requestId),
                    new UserRequestType(4),
                    new Username(DeribitSecrets.CLIENT_ID));

            message.setString(15, symbol);

            log.info("sending UserRequest: {}", message);
            sendToTarget(message, "28work", "DERIBITSERVER");
        }
    }
}
