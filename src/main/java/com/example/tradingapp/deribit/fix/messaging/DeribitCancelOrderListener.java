package com.example.tradingapp.deribit.fix.messaging;

import com.example.tradingapp.NoOrderFoundException;
import com.example.tradingapp.deribit.fix.sender.DeribitCancelOrderSender;
import com.example.tradingapp.trading.model.request.OrderRequest;
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
public class DeribitCancelOrderListener {

    private final DeribitCancelOrderSender cancelOrderSender;
    private final ObjectMapper mapper = new ObjectMapper();

    @JmsListener(destination = "order/cancel/deribit")
    public void handle(Message message) throws JMSException, SessionNotFound {
        if (message instanceof TextMessage textMessage) {
            try {
                OrderRequest orderRequest = mapper.readValue(textMessage.getText(), OrderRequest.class);
                log.info("Order request {}", orderRequest);
                cancelOrderSender.cancel(orderRequest);

            } catch (JsonProcessingException e) {
                log.error("Not valid order request: {}", e.getMessage());
            } catch (NoOrderFoundException e) {
                log.error(e.getMessage());
            }
        } else {
            log.warn("Incorrect message format");
        }
    }
}
