package com.example.tradingapp.tracker;

import com.example.tradingapp.exceptions.NoOrderFoundException;
import com.example.tradingapp.okx.trading.model.request.OrderRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@Getter
@Primary
public class OrderTrackerImpl implements OrderTracker {
    private final Map<String, Order> placedOrders = new HashMap<>();
    private final List<String> placedOrderIds = new ArrayList<>();

    public void live(String orderId) {
        var newStatus = OrderStatus.Live;
        changeStatus(newStatus, orderId);
    }

    @Override
    public void create(Order order) {
        placedOrders.put(order.getId(), order);
        placedOrderIds.add(order.getId());
        log.info("Created New Order: {}", order);
    }

    @Override
    public void failed(String orderId) {

    }

    @Override
    public void filled(String orderId, boolean fullFill) {
        var newStatus = OrderStatus.FullyFilled;
        changeStatus(newStatus, orderId);
    }

    public void partiallyFilled(String orderId) {
        log.info("Order ({}) was partially filled", orderId);
    }

    @Override
    public void canceled(String orderId) {
        var newStatus = OrderStatus.Canceled;
        changeStatus(newStatus, orderId);
    }

    private void changeStatus(OrderStatus newStatus, String orderId) {
        if (placedOrders.get(orderId) == null) {
            log.error("Can't change status to {} for Order({}), Order already removed", newStatus, orderId);
        } else {
            OrderStatus oldStatus = placedOrders.get(orderId).getStatus();
            placedOrders.get(orderId).setStatus(newStatus);

            log.info("Order ({}) status change: {} -> {}", orderId, oldStatus, newStatus);
        }
    }

    public Order findOrderByOrderRequest(OrderRequest orderRequest) throws NoOrderFoundException {
        var order = buildOrderFromOrderRequest(orderRequest);
        for (var entity : placedOrders.entrySet()) {
            if (entity.getValue().equals(order)) {
                return entity.getValue();
            }
        }
        throw new NoOrderFoundException("Can't find order, no order matches: " + orderRequest);
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