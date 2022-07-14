package com.example.tradingapp.trading;

import com.example.tradingapp.trading.model.OrderRequest;
import com.example.tradingapp.trading.model.OrderResponseStatus;

import java.io.IOException;

public interface NewOrderSender {

    OrderResponseStatus send(OrderRequest orderRequest) throws IOException;
}
