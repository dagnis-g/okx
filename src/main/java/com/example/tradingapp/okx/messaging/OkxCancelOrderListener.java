package com.example.tradingapp.okx.messaging;

import com.example.tradingapp.exceptions.NoOrderFoundException;
import com.example.tradingapp.okx.strategy.CancelOrderPolicy;
import com.example.tradingapp.okx.trading.OkxOrderTracker;
import com.example.tradingapp.okx.trading.model.request.OkxCancelOrderRequest;
import com.example.tradingapp.okx.trading.model.request.OrderRequest;
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
public class OkxCancelOrderListener {

    private final CancelOrderPolicy cancelOrderPolicy;
    private final OkxOrderTracker orderTracker;

    private final ObjectMapper mapper = new ObjectMapper();

    @JmsListener(destination = "order/cancel/okx")
    public void handle(Message message) throws IOException, JMSException {

        if (message instanceof TextMessage textMessage) {
            try {
                OrderRequest orderRequest = mapper.readValue(textMessage.getText(), OrderRequest.class);
                log.info("Cancel Order Request from Solace: {}", orderRequest);
                var order = orderTracker.findOrderByOrderRequest(orderRequest);
                cancelOrderPolicy.cancelOrder(OkxCancelOrderRequest.builder()
                        .orderId(order.getId())
                        .symbol(order.getSymbol())
                        .build());

            } catch (InvalidFormatException e) {
                log.error("Not valid OrderRequest " + e);
            } catch (NoOrderFoundException e) {
                log.error(e.getMessage());
            }
        } else {
            log.warn("Incorrect message format");
        }

    }

}
