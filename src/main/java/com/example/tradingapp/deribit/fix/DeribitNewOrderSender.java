package com.example.tradingapp.deribit.fix;

import com.example.tradingapp.trading.model.enums.OrderType;
import com.example.tradingapp.trading.model.request.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import quickfix.Message;
import quickfix.SessionNotFound;

import java.util.UUID;

import static quickfix.Session.sendToTarget;

@Slf4j
@Component
public class DeribitNewOrderSender {

    public void send(OrderRequest orderRequest) throws SessionNotFound {
        UUID uuid = UUID.randomUUID();
        Message message = new quickfix.fix44.NewOrderSingle();
        message.setString(11, String.valueOf(uuid));
        message.setString(54, "1");
        if (orderRequest.getType() == OrderType.MARKET) {
            message.setString(40, "1");
        }
        message.setString(38, String.valueOf(orderRequest.getQuantity()));
        message.setString(44, String.valueOf(orderRequest.getPrice()));
        message.setString(55, orderRequest.getSymbol());

        sendToTarget(message, "28work", "DERIBITSERVER");

        log.info("Sending order message {}", message);
    }

}
