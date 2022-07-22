package com.example.tradingapp.messaging;

import com.example.tradingapp.strategy.NewOrderPolicy;
import com.example.tradingapp.trading.model.request.OrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OkxNewOrderListener {

    private ObjectMapper mapper = new ObjectMapper();
    private final NewOrderPolicy newOrderPolicy;

    @JmsListener(destination = "order/new/okx")
    public void handle(BytesMessage message) throws IOException, JMSException {

        byte[] messageBytes = new byte[(int) message.getBodyLength()];
        message.readBytes(messageBytes);

        log.info("Solace message : " + new String(messageBytes));

        try {
            OrderRequest orderRequest = mapper.readValue(messageBytes, OrderRequest.class);
            log.info("Order Request from Solace: {}", orderRequest);
            newOrderPolicy.sendNewOrder(orderRequest);
        } catch (InvalidFormatException e) {
            log.error("Not valid OrderRequest " + e);
        }

    }

}
