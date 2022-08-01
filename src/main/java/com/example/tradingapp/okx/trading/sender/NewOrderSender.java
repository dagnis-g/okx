package com.example.tradingapp.okx.trading.sender;

import com.example.tradingapp.okx.trading.model.request.OrderRequest;
import com.example.tradingapp.okx.trading.model.response.OrderResponseStatus;

import java.io.IOException;

public interface NewOrderSender {

    OrderResponseStatus send(OrderRequest orderRequest) throws IOException;
}
