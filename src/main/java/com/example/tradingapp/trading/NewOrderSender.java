package com.example.tradingapp.trading;

import com.example.tradingapp.trading.model.OkxOrderRequest;
import com.example.tradingapp.trading.model.OrderResponseStatus;

import java.io.IOException;

public interface NewOrderSender {

    OrderResponseStatus send(OkxOrderRequest orderRequest) throws IOException;
}
