package com.example.tradingapp.trading;

import com.example.tradingapp.tracker.Order;
import org.springframework.stereotype.Component;

@Component
public class OkxOrderTracker implements OrderTracker {

    @Override
    public void create(Order order) {
        
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
