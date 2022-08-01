package com.example.tradingapp.deribit.fix.messaging;

import com.example.tradingapp.deribit.fix.sender.DeribitNewOrderSender;
import com.example.tradingapp.okx.trading.model.request.OrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import quickfix.SessionNotFound;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeribitNewOrderListener {

    private final DeribitNewOrderSender newOrderSender;
    private final ObjectMapper mapper = new ObjectMapper();

    @JmsListener(destination = "order/new/deribit")
    public void handle(Message message) throws SessionNotFound, JMSException {
        if (message instanceof TextMessage textMessage) {
            try {
                OrderRequest orderRequest = mapper.readValue(textMessage.getText(), OrderRequest.class);
                newOrderSender.send(orderRequest);
            } catch (JsonProcessingException e) {
                log.error("Not valid order request: {}", e.getMessage());
            }
        }
    }
}
