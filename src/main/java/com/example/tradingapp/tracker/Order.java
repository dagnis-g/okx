package com.example.tradingapp.tracker;

import com.example.tradingapp.trading.model.enums.OrderType;
import com.example.tradingapp.trading.model.enums.Side;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class Order {

    private String id;
    private OrderStatus status;

    private String symbol;
    private Side side;
    private OrderType type;
    private double price;
    private double quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Double.compare(order.price, price) == 0 && Double.compare(order.quantity, quantity) == 0 && Objects.equals(symbol, order.symbol) && side == order.side && type == order.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, side, type, price, quantity);
    }
}
