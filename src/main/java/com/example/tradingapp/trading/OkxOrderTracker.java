package com.example.tradingapp.trading;

import com.example.tradingapp.tracker.Order;
import com.example.tradingapp.tracker.OrderStatus;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@Getter
public class OkxOrderTracker implements OrderTracker {

    Map<String, Order> placedOrders = new HashMap<>();
    List<String> placedOrderIds = new ArrayList<>();

    public void live(String orderId) {
        var newStatus = OrderStatus.Live;

        if (placedOrders.get(orderId) == null) {
            log.error("Can't change status to {} for Order({}), Order already removed", newStatus, orderId);
        } else {
            OrderStatus oldStatus = placedOrders.get(orderId).getStatus();
            placedOrders.get(orderId).setStatus(newStatus);

            log.info("Order ({}) status change: {} -> {}", orderId, oldStatus, newStatus);
        }
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

        if (placedOrders.get(orderId) == null) {
            log.error("Can't change status to {} for Order({}), Order already removed", newStatus, orderId);
        } else {
            if (fullFill) {
                OrderStatus oldStatus = placedOrders.get(orderId).getStatus();
                placedOrders.get(orderId).setStatus(newStatus);

                log.info("Order ({}) status change: {} -> {}", orderId, oldStatus, newStatus);
            } else {
                log.info("Order ({}) was partially filled", orderId);
            }
        }
    }

    @Override
    public void canceled(String orderId) {
        var newStatus = OrderStatus.Canceled;

        if (placedOrders.get(orderId) == null) {
            log.error("Can't change status to {} for Order({}), Order already removed", newStatus, orderId);
        } else {
            OrderStatus oldStatus = placedOrders.get(orderId).getStatus();
            placedOrders.get(orderId).setStatus(newStatus);

            log.info("Order ({}) status change: {} -> {}", orderId, oldStatus, newStatus);
        }
    }

    public void removeOrderFromPlacedOrders(String orderId) {
        placedOrderIds.remove(orderId);
        placedOrders.remove(orderId);
    }

}
