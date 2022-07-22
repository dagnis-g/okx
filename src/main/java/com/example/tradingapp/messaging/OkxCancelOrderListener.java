package com.example.tradingapp.messaging;

import com.example.tradingapp.strategy.CancelOrderPolicy;
import com.example.tradingapp.tracker.Order;
import com.example.tradingapp.trading.OkxOrderTracker;
import com.example.tradingapp.trading.model.request.OkxCancelOrderRequest;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OkxCancelOrderListener {
    private final ObjectMapper mapper = new ObjectMapper();

    private final CancelOrderPolicy cancelOrderPolicy;
    private final OkxOrderTracker orderTracker;

    @JmsListener(destination = "order/cancel/okx")
    public void handle(Message message) throws IOException, JMSException {

        List<OkxCancelOrderRequest> ordersToCancel = new ArrayList<>();

        if (message instanceof TextMessage textMessage) {
            try {
                OrderRequest orderRequest = mapper.readValue(textMessage.getText(), OrderRequest.class);
                log.info("Cancel Order Request from Solace: {}", orderRequest);

                var order = Order.builder()
                        .symbol(orderRequest.getSymbol())
                        .side(orderRequest.getSide())
                        .type(orderRequest.getType())
                        .price(orderRequest.getPrice())
                        .quantity(orderRequest.getQuantity())
                        .build();

                for (var entity : orderTracker.getPlacedOrders().entrySet()) {
                    if (entity.getValue().equals(order)) {
                        ordersToCancel.add(OkxCancelOrderRequest.builder()
                                .orderId(entity.getKey())
                                .symbol(entity.getValue().getSymbol())
                                .build());
                    }
                }
                
                if (ordersToCancel.size() > 0) {
                    cancelOrderPolicy.cancelOrder(ordersToCancel.get(0));
                } else {
                    log.warn("Can't cancel, no order matches {}", orderRequest);
                }

            } catch (InvalidFormatException e) {
                log.error("Not valid OrderRequest " + e);
            }
        }

    }
}
