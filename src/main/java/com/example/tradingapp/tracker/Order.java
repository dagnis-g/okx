package com.example.tradingapp.tracker;

import com.example.tradingapp.trading.model.OrderType;
import com.example.tradingapp.trading.model.Side;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {

    private OrderStatus status;

    private String symbol;
    private Side side;
    private OrderType type;
    private double price;
    private double quantity;
}
