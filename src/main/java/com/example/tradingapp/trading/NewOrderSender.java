package com.example.tradingapp.trading;

public interface NewOrderSender {

    OrderResponseStatus send(OrderRequest orderRequest);
}
