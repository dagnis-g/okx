package com.example.tradingapp.trading;

import com.example.tradingapp.tracker.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class OkxOrderTracker implements OrderTracker {

    List<Order> newOrders = new ArrayList<>();

    @Override
    public void create(Order order) {
        log.info("Created New Order: {}", order);
        newOrders.add(order);
    }

    @Override
    public void failed(String orderId) {

    }

    @Override
    public void filled(String orderId, boolean fullFill) {

    }

    @Override
    public void canceled(String orderId) {

    }
}
