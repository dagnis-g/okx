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

                var order = buildOrderFromOrderRequest(orderRequest);

                boolean foundOrderToCancel = false;
                for (var entity : orderTracker.getPlacedOrders().entrySet()) {
                    if (entity.getValue().equals(order)) {
                        cancelOrderPolicy.cancelOrder(OkxCancelOrderRequest.builder()
                                .orderId(entity.getKey())
                                .symbol(entity.getValue().getSymbol())
                                .build());
                        foundOrderToCancel = true;
                        break;
                    }
                }

                if (!foundOrderToCancel) {
                    log.warn("Can't cancel, no order matches {}", orderRequest);
                }

            } catch (InvalidFormatException e) {
                log.error("Not valid OrderRequest " + e);
            }
        } else {
            log.warn("Incorrect message format");
        }

    }

    private Order buildOrderFromOrderRequest(OrderRequest orderRequest) {
        return Order.builder()
                .symbol(orderRequest.getSymbol())
                .side(orderRequest.getSide())
                .type(orderRequest.getType())
                .price(orderRequest.getPrice())
                .quantity(orderRequest.getQuantity())
                .build();
    }
}
