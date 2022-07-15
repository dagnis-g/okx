package com.example.tradingapp.trading;

import com.example.tradingapp.tracker.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class OkxOrderTracker implements OrderTracker {

    Map<String, Order> placedOrders = new HashMap<>();
    List<String> placedOrderId = new ArrayList<>();

    public Map<String, Order> getPlacedOrders() {
        return placedOrders;
    }

    public List<String> getPlacedOrderId() {
        return placedOrderId;
    }

    @Override
    public void create(Order order) {
        placedOrders.put(order.getId(), order);
        placedOrderId.add(order.getId());
        log.info("Created New Order: {}", order);
    }

    @Override
    public void failed(String orderId) {

    }

    @Override
    public void filled(String orderId, boolean fullFill) {

    }

    @Override
    public void canceled(String orderId) {
        placedOrderId.remove(orderId);
        placedOrders.remove(orderId);
        log.info("Removed from placed orders by ID: {}", orderId);
    }
}
