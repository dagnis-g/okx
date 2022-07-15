package com.example.tradingapp.trading.sender;

import com.example.tradingapp.trading.model.request.OrderRequest;
import com.example.tradingapp.trading.model.response.OrderResponseStatus;

import java.io.IOException;

public interface NewOrderSender {

    OrderResponseStatus send(OrderRequest orderRequest) throws IOException;
}
