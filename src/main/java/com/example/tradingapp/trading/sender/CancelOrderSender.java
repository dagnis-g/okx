package com.example.tradingapp.trading.sender;

import com.example.tradingapp.trading.model.request.OkxCancelOrderRequest;
import com.example.tradingapp.trading.model.response.OrderResponseStatus;

import java.io.IOException;

public interface CancelOrderSender {
    OrderResponseStatus cancel(OkxCancelOrderRequest cancelRequest) throws IOException;
}
