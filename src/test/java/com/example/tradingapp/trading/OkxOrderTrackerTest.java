package com.example.tradingapp.trading;

import com.example.tradingapp.exceptions.NoOrderFoundException;
import com.example.tradingapp.okx.trading.OkxOrderTracker;
import com.example.tradingapp.tracker.Order;
import com.example.tradingapp.tracker.OrderStatus;
import com.example.tradingapp.okx.trading.model.enums.OrderType;
import com.example.tradingapp.okx.trading.model.enums.Side;
import com.example.tradingapp.okx.trading.model.request.OrderRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OkxOrderTrackerTest {

    OkxOrderTracker orderTracker = new OkxOrderTracker();

    @Test
    void shouldFindOrderFromOrderRequest() throws NoOrderFoundException {
        var order = Order.builder()
                .id("123")
                .status(OrderStatus.Live)
                .side(Side.BUY)
                .symbol("ABC")
                .type(OrderType.LIMIT)
                .price(100)
                .quantity(1)
                .build();
        var orderRequest = OrderRequest.builder()
                .symbol("ABC")
                .side(Side.BUY)
                .type(OrderType.LIMIT)
                .price(100)
                .quantity(1)
                .build();
        orderTracker.getPlacedOrders().put(order.getId(), order);
        var orderFound = orderTracker.findOrderByOrderRequest(orderRequest);

        assertThat(orderFound.getId()).isEqualTo(order.getId());
        assertThat(orderFound.getStatus()).isEqualTo(order.getStatus());
        assertThat(orderFound.getSide()).isEqualTo(order.getSide());
        assertThat(orderFound.getSymbol()).isEqualTo(order.getSymbol());
        assertThat(orderFound.getType()).isEqualTo(order.getType());
        assertThat(orderFound.getPrice()).isEqualTo(order.getPrice());
        assertThat(orderFound.getQuantity()).isEqualTo(order.getQuantity());
    }

}