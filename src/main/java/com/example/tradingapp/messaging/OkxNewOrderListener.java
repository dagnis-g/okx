package com.example.tradingapp.messaging;

import com.example.tradingapp.strategy.NewOrderPolicy;
import com.example.tradingapp.trading.model.request.OrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OkxNewOrderListener {

    private final ObjectMapper mapper = new ObjectMapper();
    private final NewOrderPolicy newOrderPolicy;

    @JmsListener(destination = "order/new/okx")
    public void handle(Message message) throws IOException, JMSException {

        if (message instanceof TextMessage textMessage) {
            try {
                OrderRequest orderRequest = mapper.readValue(textMessage.getText(), OrderRequest.class);
                log.info("New Order Request from Solace: {}", orderRequest);
                newOrderPolicy.sendNewOrder(orderRequest);
            } catch (InvalidFormatException e) {
                log.error("Not valid OrderRequest " + e);
            }
        }

    }

}
