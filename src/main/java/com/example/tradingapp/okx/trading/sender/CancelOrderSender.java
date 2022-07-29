package com.example.tradingapp.okx.trading.sender;

import com.example.tradingapp.okx.trading.model.request.OkxCancelOrderRequest;
import com.example.tradingapp.okx.trading.model.response.OrderResponseStatus;

import java.io.IOException;

public interface CancelOrderSender {
    OrderResponseStatus cancel(OkxCancelOrderRequest cancelRequest) throws IOException;
}
