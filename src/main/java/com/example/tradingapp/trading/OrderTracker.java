package com.example.tradingapp.trading;

import com.example.tradingapp.tracker.Order;

public interface OrderTracker { // todo create implementation

    void create(Order order);

    void failed(String orderId);

    void filled(String orderId, boolean fullFill);

    void canceled(String orderId);
}
