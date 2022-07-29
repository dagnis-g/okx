package com.example.tradingapp.tracker;

public interface OrderTracker { // todo create implementation

    void create(Order order);

    void failed(String orderId);

    void filled(String orderId, boolean fullFill);

    void canceled(String orderId);
}
