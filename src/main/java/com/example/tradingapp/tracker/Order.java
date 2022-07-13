package com.example.tradingapp.tracker;

import com.example.tradingapp.trading.model.OrderType;
import com.example.tradingapp.trading.model.Side;

public class Order {

    private OrderStatus status;

    private String symbol;
    private Side side;
    private OrderType type;
    private double price;
    private double quantity;
}
